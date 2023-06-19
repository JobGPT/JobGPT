import { useState } from 'react';
import { useStore } from '../store';

import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';

import plus from '../assets/plus.svg';

import './NewChatBtn.css';

function NewChatBtn() {
  const addChat = useStore((store) => store.addChat);
  const [open, setOpen] = useState(false);
  const [text, setText] = useState('');

  return (
    <div className="NewChatBtn w-100">
      <Container style={{ padding: '0px' }}>
        <Row>
          <div
            className="newchat"
            onClick={() => {
              setOpen(true);
            }}
          >
            <img src={plus} className='me-2'/>
            New chat
          </div>
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
        </Row>
      </Container>
    </div>
  );
}

export default NewChatBtn;
