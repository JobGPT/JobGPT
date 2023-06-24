import { useState } from 'react';
import { useStore } from '../store';

import { Link } from 'react-router-dom';

import Row from 'react-bootstrap/Row';

import plus from '../assets/plus.svg';

import './NewChatBtn.css';

function NewChatBtn() {
  return (
    <Link to="/mainpage" style={{ padding: '0', backgroundColor: 'transparent'}}>
    <div className="NewChatBtn" style={{ width: '100%' }}>
      <div style={{ padding: '0px 5px' }}>
        <Row>
          <div
            className="newchat"
          >
            <img src={plus} className="me-2" />
            New chat
          </div>
        </Row>
      </div>
    </div>
    </Link>
  );
}

export default NewChatBtn;
