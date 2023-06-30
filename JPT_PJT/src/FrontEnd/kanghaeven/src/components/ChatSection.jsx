import './ChatSection.css';
import { useState, useEffect, useRef } from 'react';
import { useStore } from '../store.js';
import sendimg from '../assets/send.svg';
import axios from 'axios';
import { useMediaQuery } from 'react-responsive';

import MyChat from './MyChat';
import GptChat from './GptChat';

export default function ChatSection() {
  const [message, setMessage] = useState('');
  const sendmessage = useStore((store) => store.sendmessage);
  const addmessage = useStore((store) => store.addMessage);
  const addchat2 = useStore((store) => store.addChat2);
  const textareaRef = useRef(null);
  const isFirstMessage = sendmessage.length === 0;
  const company_info = useStore((store) => store.company_info);
  const save_company_info = useStore((store) => store.save_company_info);

  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });

  const handleSubmit = (event) => {
    event.preventDefault();
    const messageWithLineBreaks = message.replace(/\n/g, '<br>');
    const Requestmessage = message.split(/은|는|이|가|을|를|에/)[0];
    if (isFirstMessage) {
      const Json = `{"comp": "${Requestmessage}"}`;
      const obj = JSON.parse(Json); // JSON 형식으로 바꾸기
      console.log(obj);
      axios.post('/catch', obj).then((response) => {
        const data = response.data;
        console.log(data);
        save_company_info(data);
        console.log(company_info);
      });
      console.log('Chat:', message);
      addchat2(messageWithLineBreaks);
      addmessage(messageWithLineBreaks);
      setMessage('');
    } else {
      addmessage(messageWithLineBreaks);
      setMessage('');
    }
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
          <div
            className={`message
          ${index === 0 ? 'firstmargin' : ''}
          ${
            index === sendmessage.length - 1
              ? isLgBreakpoint
                ? 'lastmargin'
                : 'lastmargin01'
              : ''
          }
          `}
            key={index}
          >
            <MyChat message={msg} />

            <div id="info">
              {company_info.length !== 0 ? <GptChat msg={msg} /> : null}
            </div>
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
            <div className="sendbutton">
              <button type="submit" disabled={!message}>
                <img src={sendimg} alt="" />
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
