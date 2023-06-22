import './LogIn.css';
import { Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useStore } from '../store';
import { useCallback } from 'react';
import Form from 'react-bootstrap/Form';



export default function Login() {
  const { email, password, setEmail, setPassword, loginUser } = useStore();
  const { emailMessage, passwordMessage, setEmailMessage, setPasswordMessage } = useStore();
  const navigate = useNavigate();

  const validateEmail = (email) => {
    return email.toLowerCase().match(/([\w-.]+)@(([\w-]+\.)+)([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);
  };

  const validatePassword = (password) => {
    return password.toLowerCase().match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,25}$/);
  };

  const isEmailValid = validateEmail(email);
  const isPasswordValid = validatePassword(password);
  const isAllValid = isEmailValid && isPasswordValid;

  const onSubmit = (event) => {
    event.preventDefault();
    if (isAllValid) {
      if(loginUser()) {
        navigate('/mainpage');
      };
    }
  };

  const onChangeEmail = useCallback(async (event) => {
    const currentEmail = event.currentTarget.value;
    setEmail(currentEmail);

    if (!validateEmail(currentEmail)) {
      setEmailMessage('이메일 형식이 올바르지 않습니다.');
    } else {
      setEmailMessage('올바른 이메일 형식입니다.');
    }
  }, []);

  const onChangePassword = useCallback((event) => {
    const currentPassword = event.currentTarget.value;
    setPassword(currentPassword);

    if (!validatePassword(currentPassword)) {
      setPasswordMessage('영문, 숫자, 특수기호 조합으로 8자리 이상 입력해주세요.');
    } else {
      setPasswordMessage('안전한 비밀번호입니다.');
    }
  }, []);

  return (
    <div id="loginsectionwrapper">
      <div className="loginsection">
        <h1>Welcome back to JobGPT!</h1>

        <form onSubmit={onSubmit}>
          <div className='inputform'>
            <label>Email</label>
            <Form.Control
              size="lg" 
              type="email"
              value={email}
              onChange={onChangeEmail}
              className='formcontrol'
            />
            <div className='alertmessage'>
              <div className={isEmailValid ? 'success' : 'error'}>{emailMessage}</div>
            </div>
          </div>

          <div className='inputform'>
            <label>Password</label>
            <Form.Control
              size="lg" 
              type="password"
              value={password}
              onChange={onChangePassword}
              className='formcontrol'
            />
            <div className='alertmessage'>
              <div className={isPasswordValid ? 'success' : 'error'}>{passwordMessage}</div>
            </div>
          </div>

        </form>

        {isAllValid ? (
          <Button onClick={onSubmit}>Log in</Button>
        ) : (
          <Button onClick={onSubmit} disabled={!isAllValid}>
            Log in
          </Button>
        )}
        
        <div className='extralink'>
          <div className='alertmessage'>Don't have an account?</div>
          <Link to="/signup" className='signupbutton'>Sign up</Link>
        </div>
        
        {/* <div>OR</div>
        <Button>Continue with Google</Button>
        <Button>Continue with Naver</Button> */}
      </div>
    </div>
  );
}
