import './AuthPage.css';
import React, { useEffect } from "react";
import { useNavigate } from 'react-router';

const GoogleLogin = () => {
  // 아직 인가 코드가 아닌 쿼리 스트링 형태이므로 인가 코드로 만들어 줌
  const code = new URL(document.location.toString()).searchParams.get('code');
  console.log(code);
  const navigate = useNavigate();

  // 백엔드로 전달하여 액세스 토큰을 발급받아야 하므로 백엔드와 통신
  // 인가 코드 전달
  // 액세스토큰 받기

  useEffect(() => {
    fetch(`http://localhost:8080/oauth2/authorization/google`, {
      // fetch(`http://localhost:8080/oauth2/authorization/google?code=${code}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        console.log(response)
        if (response.ok) return response.json();
        throw new Error("에러 발생!");
      })
      .catch((error) => alert(error))
      .then((data) => {
        if (data.result) {
          localStorage.setItem("TOKEN", data.result);
          alert("로그인 성공");
          navigate('/mainpage');
        } else {
          alert("로그인 실패");
        }
      });
  }, []);

  return (
    <div>
      로그인 중입니다...
    </div>
  )

}

export default GoogleLogin;