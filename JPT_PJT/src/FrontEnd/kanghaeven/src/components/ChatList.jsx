import Offcanvas from 'react-bootstrap/Offcanvas';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import'./ChatList.css';

import NewChatBtn from './NewChatBtn';
import NewChat from './NewChat';
import close from '../assets/close.svg';

import { CloseButton } from 'react-bootstrap';
import { useStore } from '../store.js';
import { useMediaQuery } from 'react-responsive';


function ChatList({ handleCloseOffcanvas }) {
  const chats = useStore((store) => store.chats);
  const handleToggleOffcanvas01 = useStore((store) => store.handleToggleOffcanvas01);
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });

  return (
    <div className="bg-dark" style={{ height: '100%' }}>
      <Offcanvas.Header className="mx-2 mb-2" style={{ flexDirection: 'column' }}>
        <Row>
          <Col xs={9}>
            <NewChatBtn />
          </Col>
          {!isLgBreakpoint ? (<Col xs={3}><CloseButton onClick={handleCloseOffcanvas} /></Col>) : (<Col xs={3}>
            <Button onClick={handleToggleOffcanvas01} variant="outline-secondary">
              <img src={close} />
            </Button>
          </Col>)}
        </Row>
        <Container>
          <div className="relative" style={{ height: 'auto', opacity: 1 }}>
            <ol style={{ flexDirection: 'column' }}>
              {chats.map((chat, index) => (
                <li className="relative items-center">
                  <NewChat title={chat.title} key={index} />
                </li>
              ))}
            </ol>
          </div>
        </Container>
      </Offcanvas.Header>
    </div>
  );
}

export default ChatList;
