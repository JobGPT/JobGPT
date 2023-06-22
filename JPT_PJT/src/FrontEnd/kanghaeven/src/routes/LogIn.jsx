import { Button } from "react-bootstrap"
import './LogIn.css';


export default function Login() {
    return (
        <div id="loginsection">
            <div>
              <div>Welcome to JobGPT</div>
              <div>Log in with yout OpenAI account to continue</div>
              <Button>Log in</Button>
              <Button>Sign up</Button>              
            </div>

        </div>
    )
}