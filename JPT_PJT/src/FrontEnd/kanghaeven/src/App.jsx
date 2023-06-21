import './App.css';
import Login from './components/LogIn';

import { BrowserRouter, Route, Routes } from "react-router-dom";
import React from 'react';

import HomePage from './components/HomePage';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<Login />} />
          
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
