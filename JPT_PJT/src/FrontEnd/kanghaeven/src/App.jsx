import './App.css';
import NavBar from './components/NavBar';
import ChatSection from './components/ChatSection';

import { useMediaQuery } from 'react-responsive';
import {useStore} from './store.js';

function App() {
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });
  const showOffCanvas01 = useStore((store) => store.showOffcanvas01);
  const containerStyle = {
    display: isLgBreakpoint ? showOffCanvas01 ? 'grid' : null : null,
    gridTemplateColumns: isLgBreakpoint ? (showOffCanvas01 ? '260px auto' : null) : null
  };
  return (
    <div className="App">
      <div id="app_container" style={containerStyle}>
        <NavBar />
        <ChatSection />
      </div>
    </div>
  );
}

export default App;
