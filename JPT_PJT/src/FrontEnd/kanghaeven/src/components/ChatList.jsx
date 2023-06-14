import Offcanvas from 'react-bootstrap/Offcanvas';
import NewChatBtn from './NewChatBtn';
import { CloseButton } from 'react-bootstrap';


function ChatList({ handleCloseOffcanvas }) {
  return (
    <div className='bg-light'>
      <Offcanvas.Header className='mx-2 mb-2'>
        <NewChatBtn/>
        <CloseButton onClick={handleCloseOffcanvas}/>
      </Offcanvas.Header>
      <div className='dark'>채팅 목록 1</div>
      <div className='dark'>채팅 목록 2</div>
      <div className='dark'>채팅 목록 3</div>
      <div className='dark'>채팅 목록 4</div>
    </div>
  );
}

export default ChatList;