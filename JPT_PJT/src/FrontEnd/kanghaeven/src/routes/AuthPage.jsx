import './AuthPage.css'
import logo from '../assets/logo.svg'
import google from '../assets/google.png'
import naver from '../assets/naver.png'
import kakao from '../assets/kakao.png'
import { Button } from "react-bootstrap"
import { Link } from "react-router-dom";

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
        <Button variant="outline-primary" className="socialbutton">
          <div className="social">
            <img src={google} className="socialimg googleimg"/>
            <div>Continue with Google</div>
          </div>
        </Button>
        <Button variant="outline-primary" className="socialbutton">
          <div className="social">
            <img src={naver} className="socialimg naverimg"/>
            <div>Continue with Naver</div>
          </div>
        </Button>
        <Button variant="outline-primary" className="socialbutton">
          <div className="social">
            <img src={kakao} className="socialimg kakaoimg"/>
            <div>Continue with Kakao</div>
          </div>
        </Button>
      </div>
    </div>
  )
}