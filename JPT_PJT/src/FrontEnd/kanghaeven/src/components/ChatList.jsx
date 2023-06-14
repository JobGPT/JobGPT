import Offcanvas from 'react-bootstrap/Offcanvas';
import NewChatBtn from './NewChatBtn';
import NewChat from './NewChat';

import { CloseButton } from 'react-bootstrap';
import { useStore } from '../store.js';

function ChatList({ handleCloseOffcanvas }) {
  const chats = useStore((store) => store.chats);

  return (
    <div className="bg-light">
      <Offcanvas.Header className="mx-2 mb-2">
        <NewChatBtn />
        <CloseButton onClick={handleCloseOffcanvas} />
        <div className="relative" style={{ height: 'auto', opacity: 1 }}>
          <ol>
            {chats.map((chat) => (
              <li className="relative items-center">
                <NewChat title={chat.title} key={chat.title} />
              </li>
            ))}
          </ol>
        </div>
      </Offcanvas.Header>
    </div>
  );
}

export default ChatList;
