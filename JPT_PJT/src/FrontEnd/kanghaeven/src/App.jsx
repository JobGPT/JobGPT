import './App.css';
import NavBar from './components/NavBar';
import ChatSection from './components/ChatSection';

import { useMediaQuery } from 'react-responsive';

function App() {
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });
  const containerStyle = {
    display: isLgBreakpoint ? 'grid' : null,
    gridTemplateColumns: isLgBreakpoint ? '260px auto' : null
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
