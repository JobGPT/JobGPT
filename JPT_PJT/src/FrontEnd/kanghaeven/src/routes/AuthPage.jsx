import './AuthPage.css'
import logo from '../assets/logo.svg'
import google from '../assets/google.png'
import naver from '../assets/naver.png'
import kakao from '../assets/kakao.png'
import { Button } from "react-bootstrap"
import { Link, useNavigate } from "react-router-dom";
import React, { useEffect } from "react";
import axios from 'axios'

export default function AuthPage() {


  const restApiKey = "3af0e3dcccb71f14d3d0c5fcd29dfcce"; // process.env.REACT_APP_REST_API_KEY;

  const redirectUrl = "http://localhost:5173/kakaoLogin"; // process.env.REACT_APP_REDIRECT_URL;

  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

  const kakaoLoginHandler = () => {
    window.location.href = kakaoAuthUrl;
  };

  // const KakaoLogin = () => {
  //   const navigate = useNavigate();
  //   const code = new URL(window.location.href).searchParams.get("code");
  //   const BASE_URL = // process.env.REACT_APP_BASE_URL;

  //   useEffect(() => {
  //     const kakao = async () => {
  //       return await axios
  //         .get(`${BASE_URL}/api/v1/members/kakaoLogin?code=${code}`)
  //         .then((res) => setCookie("token", res.headers.authorization))
  //           .then(() => {
  //             navigate("/mainpage");
  //         })
  //     };
  //     if (code) {
  //       AuthPage();
  //     }
  //   }, [
  //     code,
  //     navigate
  //   ]);

  //   return <div>로딩페이지 컴포넌트</div>
  // }
  
  const clientID = "cvYMn0uNR2j3P2nIoHDh"; // process.env.REACT_APP_CLIENT_ID;

  const stateString = "YPeI6c7pem"; // process.env.REACT_APP_STATE_STRING;

  const callbackUrl = "http://localhost:5173/naverLogin"; // process.env.REACT_APP_CALLBACK_URL;

  const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?client_id=${clientID}&response_type=code&redirect_uri=${callbackUrl}&state=${stateString}`;

  const naverLoginHandler = () => {
    window.location.href = naverAuthUrl;
  };


  const navigate = useNavigate();
  const code = new URL(window.location.href).searchParams.get("code");
  const state = new URL(window.location.href).searchParams.get("state");

  const BASE_URL = "http://localhost:3000"; // process.env.REACT_APP_BASE_URL;

  useEffect(() => {
    const naver = async () => {
      return await axios
        .get(
          `${BASE_URL}/oauth2/authorization/naver?code=${code}&state=${state}`
        )
        .then((res) => setCookie("token", res.headers.authorization))
        .then(() => {
          navigate("/mainpage");
        })
    };
    if (code) {
      naver();
    }
  }, [code]);


  return (
    <div id="authsection">
      <div className="auth">
        <img src={logo} alt="" className='logoimg' />
        <div className="text">Welcome to JobGPT</div>
        <div className="text">Log in with your JobGPT account to continue</div>
        <div className="buttons">
          <Link to="/login" className='button'>
            <Button className="buttonlogin">Log in</Button>
          </Link>
          <Link to="/signup" className='button'>
            <Button className="buttonsignup">Sign up</Button>  
          </Link>
        </div>

        <div>or</div>

        <Button
        variant="outline-primary" className="socialbutton">
          <div className="social">
            <img src={google} className="socialimg googleimg"/>
            <div>Continue with Google</div>
          </div>
        </Button>
        <Button onClick={naverLoginHandler}
        variant="outline-primary" className="socialbutton">
          <div className="social">
            <img src={naver} className="socialimg naverimg"/>
            <div>Continue with Naver</div>
          </div>
        </Button>
        <Button onClick={kakaoLoginHandler}
        variant="outline-primary" className="socialbutton">
          <div className="social">
            <img src={kakao} className="socialimg kakaoimg"/>
            <div>Continue with Kakao</div>
          </div>
        </Button>
      </div>
    </div>
  )
}
