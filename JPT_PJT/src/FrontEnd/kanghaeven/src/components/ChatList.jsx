import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Row from 'react-bootstrap/Row';

import './ChatList.css';

import close from '../assets/close.svg';
import NewChat from './NewChat';
import NewChatBtn from './NewChatBtn';
import Profile from './Profile';

import { CloseButton } from 'react-bootstrap';
import { useMediaQuery } from 'react-responsive';
import { useStore } from '../store.js';


function ChatList({ handleCloseOffcanvas }) {
  const chats = useStore((store) => store.chats);
  console.log(chats);
  const handleToggleOffcanvas01 = useStore((store) => store.handleToggleOffcanvas01);
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });

  return (
    <div className="bg-dark" style={{ height: '100%' }}>
      <Offcanvas.Header style={{ flexDirection: 'column' }}>
        <Row style={{ width: '100%' }}>
          <Col xs={9} style={{ padding: '0px' }}>
              <NewChatBtn />
          </Col>
          {!isLgBreakpoint ? (
            <Col xs={3} className="colclose">
              <CloseButton onClick={handleCloseOffcanvas} />
            </Col>
          ) : (
            <Col xs={3}>
              <Button id="closebtn" onClick={handleToggleOffcanvas01} variant="outline-secondary">
                <img src={close} className="closeimg" />
              </Button>
            </Col>
          )}
        </Row>
        <div className="relative" style={{ height: 'auto', width: '100%' }}>
          <ol style={{ flexDirection: 'column', padding: '0px' }}>
            {chats.map((chat) => (
                <li className="relative items-center" key={chat.id}>
                  <NewChat title={chat.title} id={chat.id} />
                </li>
            ))}
          </ol>
        </div>
      </Offcanvas.Header>
      <div id="profilebox">
        <Profile />
      </div>
    </div>
  );
}

export default ChatList;
