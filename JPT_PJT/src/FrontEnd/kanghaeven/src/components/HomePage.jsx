import React from 'react';

import RouterView from '../routes/RouterView';

import { useMediaQuery } from 'react-responsive';
import { useStore } from '../store.js';

import './HomePage.css';

export default function HomePage() {
  const isLgBreakpoint = useMediaQuery({ minWidth: 992 });
  const showOffCanvas01 = useStore((store) => store.showOffcanvas01);
  const containerStyle = {
    display: isLgBreakpoint ? (showOffCanvas01 ? 'grid' : null) : null,
    gridTemplateColumns: isLgBreakpoint ? (showOffCanvas01 ? '260px auto' : null) : null,
  };
  return (
    <div id="app_container" style={containerStyle}>
      <RouterView />
    </div>
  );
}
