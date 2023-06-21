import './App.css';
import NavBar from './components/NavBar';
import ChatSection from './components/ChatSection';
import Login from './components/LogIn';

import { useMediaQuery } from 'react-responsive';
import { useStore } from './store.js';
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });
  const showOffCanvas01 = useStore((store) => store.showOffcanvas01);
  const containerStyle = {
    display: isLgBreakpoint ? showOffCanvas01 ? 'grid' : null : null,
    gridTemplateColumns: isLgBreakpoint ? (showOffCanvas01 ? '260px auto' : null) : null
  };
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <Route path="/"
            element={
              <div id="app_container" style={containerStyle}>
                <NavBar />
                <ChatSection />
              </div>}
          />
          <Route path="/login" element={<Login />} />
          
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
