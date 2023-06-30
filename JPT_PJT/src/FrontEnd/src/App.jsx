import './App.css';
import ChatSection from './components/ChatSection';
import ChatList from './components/ChatList';
// import Base from './components/Base';
// import {BrowserRouter, Routes, Route, Link} from "react-router-dom";
// import Home from './components/Home'

function App() {
  return (
    <div className="App">
      <ChatList />
      <ChatSection />
    </div>
  );
}

export default App;