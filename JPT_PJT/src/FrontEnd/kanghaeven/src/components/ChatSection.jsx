import './ChatSection.css';
import { useState, useEffect, useRef } from 'react';
import sendimg from '../assets/send.svg';

import MyChat from './MyChat';

export default function ChatSection() {
  const [message, setMessage] = useState('');
  const [sendmessage, setSendMessage] = useState([]);
  const textareaRef = useRef(null);

  const handleSubmit = (event) => {
    event.preventDefault();
    const messageWithLineBreaks = message.replace(/\n/g, "<br>");
    console.log('Chat:', message);
    setSendMessage((prevMessage) => [...prevMessage, messageWithLineBreaks]);
    setMessage('');
  };

  const handleChange = (event) => {
    setMessage(event.target.value);
  };

  useEffect(() => {
    adjustTextareaHeight();
  }, [message]);

  const adjustTextareaHeight = () => {
    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
    }
  };

  return (
    <div className="container">
      {!sendmessage.length && (
        <div className="first">
          <h1>JobGPT</h1>
          Here Is ChatSection
        </div>
      )}
      <div className="messagemap">
        {sendmessage.map((msg, index) => (
          <div className="message">
            <MyChat key={index} message={msg} />
          </div>
       ))}
      </div>

      <div id="chatinsection">
        <form id="chatingform" onSubmit={handleSubmit}>
          <div className="chatsection">
            <textarea
              ref={textareaRef}
              id="textarea"
              rows="1"
              placeholder="Send a message."
              value={message}
              onChange={handleChange}
            ></textarea>
            <button type="submit" disabled={!message} >
              <img src={sendimg} alt="" />
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
