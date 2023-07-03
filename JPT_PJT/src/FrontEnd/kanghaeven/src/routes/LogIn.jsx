import './LogIn.css';
import { Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useStore } from '../store';
import { useCallback, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import { fetchLoginUser } from '../api/auth';

export default function Login() {
  const { 
    username, 
    password, 
    setUsername, 
    setPassword 
  } = useStore();

  const { 
    usernameMessage, 
    passwordMessage, 
    setUsernameMessage, 
    setPasswordMessage 
  } = useStore();

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

  const loginUser = async () => {
    try {
      console.log('Username:', useStore.getState().username);
      console.log('Password:', useStore.getState().password);
      const data = {
        username: useStore.getState().username,
        password: useStore.getState().password,
      };
      const res = await fetchLoginUser(data);
      console.log(res.config.method);
      if (res.config.method === 'post') {
        // AccessToken 값 저장
        const accessToken = res.headers.accesstoken;
        localStorage.setItem("accessToken", accessToken);
        useStore.setState({ accesstoken: accessToken });
        console.log('accessToken:', accessToken);

        // RefreshToken 값 저장
        const refreshToken = res.headers['refreshtoken'];
        localStorage.setItem("refreshToken", refreshToken);
        useStore.setState({ refreshtoken: refreshToken });
        console.log('refreshToken:', refreshToken);

      };
      return true;
    } catch (err) {
      console.log(err);
      if (err.response.status === 400) {
        alert( err.response.data )
      };
      return false;
    };
  };

  const onSubmit = async(event) => {
    event.preventDefault();
    if (isAllValid) {
      const success = await loginUser();
      if (success) {
        navigate('/mainpage');
      } else {
        navigate('/');
      };
    };
  };

  const onChangeusername = useCallback(async (event) => {
    const currentUsername = event.currentTarget.value;
    setUsername(currentUsername);

    if (!validateUsername(currentUsername)) {
      setUsernameMessage('닉네임 형식이 올바르지 않습니다.');
    } else {
      setUsernameMessage('올바른 닉네임 형식입니다.');
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
