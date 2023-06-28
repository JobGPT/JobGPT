import './AuthPage.css';
import React from "react";
import naver from '../assets/naver.png';
import { Button } from "react-bootstrap";

const Naver = () => {
  const clientID = "cvYMn0uNR2j3P2nIoHDh"; // process.env.REACT_APP_CLIENT_ID;

  const stateString = "YPeI6c7pem"; // process.env.REACT_APP_STATE_STRING;

  const callbackUrl = "http://localhost:5173/naverLogin"; // process.env.REACT_APP_CALLBACK_URL;

  const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?client_id=${clientID}&response_type=code&redirect_uri=${callbackUrl}&state=${stateString}`;

  const loginHandler = () => {
    // 인가 코드 발급
    window.location.href = naverAuthUrl;
  };

  return (
    <Button onClick={loginHandler}
    variant="outline-primary" className="socialbutton">
      <div className="social">
        <img src={naver} className="socialimg naverimg"/>
        <div>Continue with Naver</div>
      </div>
    </Button>
  )

}

export default Naver;