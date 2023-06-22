import React, { useEffect } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import { useStore } from '../store.js';

import MainPage from './MainPage';
import Login from './LogIn';
import ChatPage from './ChatPage';

export default function RouterView() {
  return (
    <Routes>
      <Route path={'/'} element={<Login />} />
      <Route path={'/mainpage'} exact element={<MainPage/>} />
      <Route path={'/mainpage/:index'} exact element={<ChatPage/>} />
    </Routes>
  );
}
