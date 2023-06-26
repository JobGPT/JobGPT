import React from 'react';

import './Corporation.css';

export default function Corporation({ companyInfo, obj, jobPostings }) {
  return (
    <div id="corp_container">
      <div id="corp_top">
        <h3 id="basic_info">기본정보</h3>
      </div>
      <table id="info_container">
        <tbody>
          <tr>
            <th>기업명</th>
            <td>{companyInfo.name}</td>
            <th>설립일</th>
            <td>{obj.summery.company_overview['table-0']['설립일']}</td>
          </tr>
          <tr>
            <th>대표자</th>
            <td>{obj.summery.company_overview['table-0']['대표자']}</td>
            <th>사원 수</th>
            <td>{companyInfo.numberOfEmployees}명</td>
          </tr>
          <tr>
            <th>기업규모</th>
            <td>{obj.summery.company_overview['table-0']['기업규모']}</td>
            <th>채용공고 수</th>
            <td>{Object.keys(jobPostings).length}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
