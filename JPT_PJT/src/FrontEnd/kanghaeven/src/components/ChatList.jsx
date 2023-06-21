import Offcanvas from 'react-bootstrap/Offcanvas';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import './ChatList.css';

import Profile from './Profile';
import NewChatBtn from './NewChatBtn';
import NewChat from './NewChat';
import close from '../assets/close.svg';

import { CloseButton } from 'react-bootstrap';
import { useStore } from '../store.js';
import { useMediaQuery } from 'react-responsive';

import { Link } from 'react-router-dom';

function ChatList({ handleCloseOffcanvas }) {
  const chats = useStore((store) => store.chats);
  const handleToggleOffcanvas01 = useStore((store) => store.handleToggleOffcanvas01);
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });

  return (
    <div className="bg-dark" style={{ height: '100%' }}>
      <Offcanvas.Header style={{ flexDirection: 'column' }}>
        <Row style={{ width: '100%' }}>
          <Col xs={9} style={{ padding: '0px' }}>
            <Link to="/">
              <NewChatBtn />
            </Link>
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
              <li className="relative items-center">
                <NewChat title={chat.title} index={chat.index} key={chat.index} />
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
