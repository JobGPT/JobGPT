import './AuthPage.css';
import logo from '../assets/logo.svg';
import google from '../assets/google.png';
import KakaoLoginBtn from './KakaoLogInBtn';
import NaverLoginBtn from './NaverLogInBtn';
import GoogleLoginBtn from './GoogleLogInBtn';
import { Button } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import React, { useEffect } from "react";
import axios from 'axios';

export default function AuthPage() {



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

        <GoogleLoginBtn />
        <NaverLoginBtn />
        <KakaoLoginBtn />
      </div>
    </div>
  )
}
