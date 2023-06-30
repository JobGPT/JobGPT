import './SignUp.css';

import { Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useStore } from '../store';
import { useCallback, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import { fetchSignupUser } from '../api/auth';

export default function SignUp() {
  const {
    email,
    password,
    confirmPassword,
    username,
    setEmail,
    setPassword,
    setConfirmPassword,
    setUsername,
    signupUser,
  } = useStore();

  const {
    emailMessage,
    passwordMessage,
    confirmPasswordMessage,
    usernameMessage,
    setEmailMessage,
    setPasswordMessage,
    setConfirmPasswordMessage,
    setUsernameMessage,
  } = useStore();
  const navigate = useNavigate();

  const Reset = () => {
    setUsername('');
    setPassword('');
    setConfirmPassword('');
    setEmail('');
    setUsernameMessage('');
    setPasswordMessage('');
    setConfirmPasswordMessage('');
    setEmailMessage('');
  };

  useEffect(() => {
    Reset();
  }, []);

  // 이메일, 비밀번호, 닉네임 유효성 검사
  // 닉네임 1~8자 길이, 첫번째 문자 한글/영문대소/숫자 중 하나, 그 후 어떤 문자든 허용
  // 비밀번호 10~25자 길이, 영문자/숫자/특수문자 각 하나 이상
  // 이메일 형식이 맞는지 ex) english@email.com

  const validateUsername = (username) => {
    return username.toLowerCase().match(/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|].{1,8}$/);
  };

  const validatePassword = (password) => {
    return password
      .toLowerCase()
      .match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,25}$/);
  };

  const validateEmail = (email) => {
    return email
      .toLowerCase()
      .match(/([\w-.]+)@(([\w-]+\.)+)([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);
  };

  const isUsernameValid = validateUsername(username);
  const isPasswordValid = validatePassword(password);
  const isConfirmPasswordValid = password === confirmPassword;
  const isEmailValid = validateEmail(email);
  const isAllValid =
    isEmailValid && isPasswordValid && isConfirmPasswordValid && isUsernameValid;

  const onSubmit = (event) => {
    event.preventDefault();
    if (isAllValid) {
      if (signupUser()) {
        navigate('/');
      }
    }
  };

  const onChangeUsername = useCallback((event) => {
    const currentUsername = event.currentTarget.value;
    setUsername(currentUsername);

    if (!validateUsername(currentUsername)) {
      setUsernameMessage('1글자 이상 9글자 미만으로 입력해주세요.');
    } else {
      setUsernameMessage('사용할 수 있는 닉네임입니다.');
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

  const onChangeConfirmPassword = useCallback(
    (event) => {
      const currentConfirmPassword = event.currentTarget.value;
      setConfirmPassword(currentConfirmPassword);

      if (currentConfirmPassword !== password) {
        setConfirmPasswordMessage('비밀번호가 일치하지 않습니다.');
      } else {
        setConfirmPasswordMessage('올바른 비밀번호입니다.');
      }
    },
    [password]
  );

  const onChangeEmail = useCallback(async (event) => {
    const currentEmail = event.currentTarget.value;
    setEmail(currentEmail);

    if (!validateEmail(currentEmail)) {
      setEmailMessage('이메일 형식이 올바르지 않습니다.');
    } else {
      setEmailMessage('올바른 이메일 형식입니다.');
    }
  }, []);

  return (
    <div id="signupsectionwrapper">
      <div className="signupsection">
        <h1>Create your account</h1>
        <form onSubmit={onSubmit}>
          <div className="inputform">
            <label>Username</label>
            <Form.Control
              size="md"
              type="text"
              value={username}
              onChange={onChangeUsername}
              className="formcontrol"
            />
            <div className="alertmessage">
              <div className={isUsernameValid ? 'success' : 'error'}>
                {usernameMessage}
              </div>
            </div>
          </div>

          <div className="inputform">
            <label>Password</label>
            <Form.Control
              size="md"
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

          <div className="inputform">
            <label>Confirm Password</label>
            <Form.Control
              size="md"
              type="password"
              value={confirmPassword}
              onChange={onChangeConfirmPassword}
              className="formcontrol"
            />
            <div className="alertmessage">
              <div className={isConfirmPasswordValid ? 'success' : 'error'}>
                {confirmPasswordMessage}
              </div>
            </div>
          </div>

          <div className="inputform">
            <label>Email</label>
            <Form.Control
              size="md"
              type="email"
              value={email}
              onChange={onChangeEmail}
              className="formcontrol"
            />
            <div className="alertmessage">
              <div className={isEmailValid ? 'success' : 'error'}>{emailMessage}</div>
            </div>
          </div>
        </form>

        {isAllValid ? (
          <Button onClick={onSubmit}>Sign up</Button>
        ) : (
          <Button onClick={onSubmit} disabled={!isAllValid}>
            Sign up
          </Button>
        )}

        <div className="extralink">
          <div className="alertmessage">Already have an account?</div>
          <Link to="/login" className="loginbutton">
            Log in
          </Link>
        </div>

        {/* <div>OR</div>
        <Button>Continue with Google</Button>
        <Button>Continue with Naver</Button>   */}
      </div>
    </div>
  );
}
