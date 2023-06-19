import profileimg from '../assets/profile.svg';
import './MyChat.css';

export default function MyChat({ message }) {
  return (
    <div id="container" >
      <div id="chat_container">
          <div id="current_chat">
            <img src={profileimg} alt="" className='profileimg' style={{backgroundColor: 'grey', height: '26px', margin: '1.5rem'}}/>
            <div>
              <span dangerouslySetInnerHTML={{ __html: message}} />
            </div>
            
          </div>
      </div>
      <hr style={{margin: '0px'}}/>
    </div>
  );
}
