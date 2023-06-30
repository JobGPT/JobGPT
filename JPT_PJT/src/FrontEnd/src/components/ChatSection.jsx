import './ChatSection.css';
import { useState } from 'react';
import sendimg from '../assets/send.svg';

import MyChat from './MyChat';

export default function ChatSection() {
  const [message, setMessage] = useState('');
  const [sendmessage, setSendMessage] = useState([]);

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('Chat:', message);
    setSendMessage((prevMessage) => [...prevMessage, message]);
    setMessage('');
  };

  const handleChange = (event) => {
    setMessage(event.target.value);
  };


  return (
    <div className="container">
      {!sendmessage.length && <div className="first">
        <h1>JobGPT</h1>
        Here Is ChatSection
      </div>}
      {sendmessage.map((msg, index) => (
        <MyChat key={index} message={msg} />
      ))}
      <div id="chatinsection">
        <form id="chatingform" onSubmit={handleSubmit}>
          <div className="chatsection">
            <textarea
              tabIndex="0"
              id="textarea"
              rows="1"
              placeholder="Send a message."
              value={message}
              onChange={handleChange}
            ></textarea>
            <button type="submit" disabled={!message}>
              <img src={sendimg} alt="" />
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}