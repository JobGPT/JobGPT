import profileimg from '../assets/profile.svg';

export default function MyChat({ message }) {
  return (
    <div id="container">
      <div id="chat_container">
        <div id="profile">
          <img src={profileimg} alt="" />
        </div>
        <div id="current_chat"><div dangerouslySetInnerHTML={{ __html: message }} /></div>
      </div>
      <hr />
    </div>
  );
}
