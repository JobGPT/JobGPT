import React from 'react';
import './CorpLogo.css';

export default function CorpLogo({ companyInfo, comp_page }) {
  return (
    <div style={{ display: 'table', height: '136px' }}>
      <div id="logo">
        <img src={companyInfo.logo} />
      </div>
      <div id="name">
        <h3 id="logo_name">
          {companyInfo.name}
          <a href={comp_page} target="_blank" id="link">
            홈페이지
          </a>
        </h3>
        <p id="cate">{companyInfo.makesOffer}</p>
      </div>
    </div>
  );
}
