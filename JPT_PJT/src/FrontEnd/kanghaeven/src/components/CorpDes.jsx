import React from 'react';

import './CorpDes.css';

export default function CorpDes({ companyInfo, des }) {
  return (
    <div id="description_box">
      <div id="des_top">
        <h3 id="cop_name">{companyInfo.name}?</h3>
      </div>
      <div dangerouslySetInnerHTML={{ __html: des }} id="description"></div>
    </div>
  );
}
