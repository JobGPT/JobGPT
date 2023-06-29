import './LogIn.css';
import { Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useStore } from '../store';
import { useCallback, useEffect } from 'react';
import Form from 'react-bootstrap/Form';

export default function Login() {
  const { username, password, setUsername, setPassword, loginUser } = useStore();
  const { usernameMessage, passwordMessage, setUsernameMessage, setPasswordMessage } =
    useStore();
  const navigate = useNavigate();

  const Reset = () => {
    setUsername('');
    setPassword('');
    setUsernameMessage('');
    setPasswordMessage('');
  };

  useEffect(() => {
    Reset();
  }, []);

  const validateUsername = (username) => {
    return username.toLowerCase().match(/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|].{1,8}$/);
  };

  const validatePassword = (password) => {
    return password
      .toLowerCase()
      .match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,25}$/);
  };

  const isusernameValid = validateUsername(username);
  const isPasswordValid = validatePassword(password);
  const isAllValid = isusernameValid && isPasswordValid;

  const onSubmit = (event) => {
    event.preventDefault();
    if (isAllValid) {
      if (loginUser()) {
        navigate('/mainpage');
      }
    }
  };

  const onChangeusername = useCallback(async (event) => {
    const currentUsername = event.currentTarget.value;
    setUsername(currentUsername);

    if (!validateUsername(currentUsername)) {
      setUsernameMessage('이메일 형식이 올바르지 않습니다.');
    } else {
      setUsernameMessage('올바른 이메일 형식입니다.');
    }
  }, []);

  const onChangePassword = useCallback((event) => {
    const currentPassword = event.currentTarget.value;
    setPassword(currentPassword);

    if (!validatePassword(currentPassword)) {
      setPasswordMessage('영문, 숫자, 특수기호 조합으로 10자리 이상 입력해주세요.');
    } else {
      setPasswordMessage('안전한 비밀번호입니다.');
    }
  }, []);

  return (
    <div id="loginsectionwrapper">
      <div className="loginsection">
        <h1>Welcome back to JobGPT!</h1>

        <form onSubmit={onSubmit}>
          <div className="inputform">
            <label>Username</label>
            <Form.Control
              size="lg"
              type="username"
              value={username}
              onChange={onChangeusername}
              className="formcontrol"
            />
            <div className="alertmessage">
              <div className={isusernameValid ? 'success' : 'error'}>
                {usernameMessage}
              </div>
            </div>
          </div>

          <div className="inputform">
            <label>Password</label>
            <Form.Control
              size="lg"
              type="password"
              value={password}
              onChange={onChangePassword}
              className="formcontrol"
            />
            <div className="alertmessage">
              <div className={isPasswordValid ? 'success' : 'error'}>
                {passwordMessage}
              </div>
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

        <div className="extralink">
          <div className="alertmessage">Don't have an account?</div>
          <Link to="/signup" className="signupbutton">
            Sign up
          </Link>
        </div>
      </div>
    </div>
  );
}
