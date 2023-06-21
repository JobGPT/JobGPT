import './App.css';
import Login from './components/LogIn';

import { BrowserRouter, Route, Routes } from "react-router-dom";
import React from 'react';
import { BrowserRouter } from 'react-router-dom';

import HomePage from './components/HomePage';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <HomePage />
          <Route path="/login" element={<Login />} />
          
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
