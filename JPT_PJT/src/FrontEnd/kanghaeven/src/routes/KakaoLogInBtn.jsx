import './AuthPage.css';
import React from "react";
import kakao from '../assets/kakao.png';
import { Button } from "react-bootstrap";

const Kakao = () => {
  const restApiKey = "3af0e3dcccb71f14d3d0c5fcd29dfcce"; // process.env.REACT_APP_REST_API_KEY;

  const redirectUrl = "http://localhost:5173/kakaoLogin"; // process.env.REACT_APP_REDIRECT_URL;

  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

  const loginHandler = () => {
    // 인가 코드 발급
    window.location.href = kakaoAuthUrl;
  };

  return (
    <Button onClick={loginHandler}
    variant="outline-primary" className="socialbutton">
      <div className="social">
        <img src={kakao} className="socialimg kakaoimg"/>
        <div>Continue with Kakao</div>
      </div>
    </Button>
  )

}

export default Kakao;