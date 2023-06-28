import './AuthPage.css';
import React from "react";
import google from '../assets/google.png';
import { Button } from "react-bootstrap";

const Google = () => {
  const googleClientID = "531047025202-lgr6val5f8kmceqq7alru4o0l6e7f5i7.apps.googleusercontent.com"; // process.env.REACT_APP_GOOGLE_KEY;

  const googleRedirectUrl = "http://localhost:5173/googleLogin"; // process.env.REACT_APP_GOOGLE_REDIRECT_URL;

  const googleAuthUrl = `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${googleClientId}&scope=openid%20profile%20email&redirect_uri=${googleRedirectUrl}`;

  const loginHandler = () => {
    // 인가 코드 발급
    window.location.href = googleAuthUrl;
  };

  return (
    <Button onClick={loginHandler}
    variant="outline-primary" className="socialbutton">
      <div className="social">
        <img src={google} className="socialimg googleimg"/>
        <div>Continue with Google</div>
      </div>
    </Button>
  )

}

export default Google;