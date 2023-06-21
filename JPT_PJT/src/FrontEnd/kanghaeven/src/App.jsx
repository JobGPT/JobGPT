import './App.css';

import { BrowserRouter } from "react-router-dom";
import React from 'react';

import HomePage from './components/HomePage';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <HomePage />          
      </div>
    </BrowserRouter>
  );
}

export default App;
