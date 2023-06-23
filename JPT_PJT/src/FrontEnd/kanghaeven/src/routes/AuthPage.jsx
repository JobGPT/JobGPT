import './AuthPage.css'
import logo from '../assets/logo.svg'
import { Button } from "react-bootstrap"
import { Link } from "react-router-dom";

export default function AuthPage() {

  return (
    <div id="authsection">
      <div className="auth">
        <img src={logo} alt="" className='logoimg' />
        <div className="text">Welcome to JobGPT</div>
        <div className="text">Log in with your OpenAI account to continue</div>
        <div className="buttons">
          <Link to="/login" className='button'>
            <Button className="buttonlogin">Log in</Button>
          </Link>
          <Link to="/signup" className='button'>
            <Button>Sign up</Button>  
          </Link>
        </div>
      </div>
    </div>
  )
}