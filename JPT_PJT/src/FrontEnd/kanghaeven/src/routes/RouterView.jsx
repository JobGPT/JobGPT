import React from 'react';
import { Routes, Route } from 'react-router-dom';

import AuthPage from './AuthPage';
import LogIn from './LogIn';
import KakaoLogIn from './KakaoLogIn';
import NaverLogIn from './NaverLogIn';
import GoogleLogIn from './GoogleLogIn';
import SignUp from './SignUp';
import MainPage from './MainPage';
import ChatPage from './ChatPage';

export default function RouterView() {
  return (
    <Routes>
      <Route path={'/'} element={<AuthPage />} />
      <Route path={'/login'} element={<LogIn />} />
      <Route path={'/kakaoLogin'} element={<KakaoLogIn />} />
      <Route path={'/naverLogin'} element={<NaverLogIn />} />
      <Route path={'/googleLogin'} element={<GoogleLogIn />} />
      <Route path={'/signup'} element={<SignUp />} />
      <Route path={'/mainpage'} exact element={<MainPage/>} />
      <Route path={'/mainpage/:index'} exact element={<ChatPage/>} />
    </Routes>
  );
}
