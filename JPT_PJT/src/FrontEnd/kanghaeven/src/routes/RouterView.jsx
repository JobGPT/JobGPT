import React from 'react';
import { Routes, Route } from 'react-router-dom';

import AuthPage from './AuthPage';
import LogIn from './LogIn';
import SignUp from './SignUp';
import MainPage from './MainPage';

export default function RouterView() {
  return (
    <Routes>
      <Route path={'/'} element={<AuthPage />} />
      <Route path={'/login'} element={<LogIn />} />
      <Route path={'/signup'} element={<SignUp />} />
      <Route path={'/mainpage'} element={<MainPage />} />
      
    </Routes>
  );
}
