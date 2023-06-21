import React from 'react';
import { Routes, Route } from 'react-router-dom';

import MainPage from './MainPage';
import Login from './LogIn';

export default function RouterView() {
  return (
    <Routes>
      <Route path={'/'} element={<MainPage />} />
      <Route path={'/login'} element={<Login />} />
      
    </Routes>
  );
}
