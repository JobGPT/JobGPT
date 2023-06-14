import './ChatList.css';
import plus from '../assets/plus.svg';
import profile from '../assets/profile.svg';
import NewChat from './NewChat';
import { useStore } from '../store';
import { useState } from 'react';

export default function ChatList() {
  const addChat = useStore((store) => store.addChat);
  const chats = useStore((store) => store.chats);
  const [open, setOpen] = useState(false);
  const [text, setText] = useState('');

  return (
    <div className="container">
      <nav className="List">
        <a
          className="newchat"
          onClick={() => {
            setOpen(true);
          }}
        >
          <img src={plus} />
          New chat
        </a>
        <div className="relative" style={{ height: 'auto', opacity: 1 }}>
          <ol>
            {chats.map((chat) => (
              <li className="relative items-center">
                <NewChat title={chat.title} key={chat.title} />
              </li>
            ))}
            {open && (
              <div className="Modal">
                <div className="modalContent">
                  <input onChange={(e) => setText(e.target.value)} value={text} />
                  <button
                    onClick={() => {
                      addChat(text);
                      setText('');
                      setOpen(false);
                    }}
                  >
                    Submit
                  </button>
                </div>
              </div>
            )}
          </ol>
        </div>
        <div className="profile">
          <a href="" className="company">
            <img src={profile} alt="" />
            <span className="companList">지원한 기업</span>
          </a>
        </div>
      </nav>
    </div>
  );
}
