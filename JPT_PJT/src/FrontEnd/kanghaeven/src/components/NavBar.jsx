import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
import { useMediaQuery } from 'react-responsive';

import plus from '../assets/plus.svg';
import close from '../assets/close.svg';

import { useStore } from '../store.js';

import ChatList from './ChatList';

function NavBar() {
  const [showOffcanvas, setShowOffcanvas] = useState(false);
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });

  const divStyle = {
    height: isLgBreakpoint ? '100%' : 'auto',
  };
  const showOffcanvas01 = useStore((store) => store.showOffcanvas01);
  const handleToggleOffcanvas01 = useStore((store) => store.handleToggleOffcanvas01);

  const handleCloseOffcanvas = () => {
    console.log('닫기버튼 클릭');
    setShowOffcanvas(false);
  };

  const handleToggleOffcanvas = () => {
    console.log('작은 화면 토글 메뉴');
    setShowOffcanvas(!showOffcanvas);
  };

  return (
    <div style={divStyle}>
      {/* 작은 화면에서 보이는 네비게이션 바 */}
      <Navbar key="lg" bg="light" expand="lg" className="p-3 d-lg-none" sticky='top'>
        <Container fluid>
          <Navbar.Toggle aria-controls={`offcanvasNavbar-expand-lg`} onClick={handleToggleOffcanvas} style={{ alignItems: 'center' }}/>

          <Offcanvas show={showOffcanvas} onHide={handleCloseOffcanvas} placement="start">
            <Offcanvas.Body className="p-0">
              <ChatList handleCloseOffcanvas={handleCloseOffcanvas}/>
            </Offcanvas.Body>
          </Offcanvas>

          <h5>New chat</h5>

          <Button className="px-3" variant="outline-dark">
            <img src={plus} alt="새채팅생성버튼" />
          </Button>
        </Container>
      </Navbar>

      {/* 큰 화면에서만 보이는 사이드 바 채팅 목록 */}
      {isLgBreakpoint ? (
        <div style={{ height: '100%', width: '100%' }}>
          {!showOffcanvas01 && (
            <Button onClick={handleToggleOffcanvas01} variant="outline-dark">
              <img src={close} />
            </Button>
          )}
          {showOffcanvas01 && (
            <div className="p-3 bg-dark" style={{ height: '100%' }}>
              <ChatList />
            </div>
          )}
        </div>
      ) : null}
    </div>
  );
}

export default NavBar;