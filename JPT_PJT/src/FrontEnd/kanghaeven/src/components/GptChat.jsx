import React from 'react';
import { useStore } from '../store.js';

import './GptChat.css';

export default function GptChat({msg}) {
  const company_info = useStore((store) => store.company_info);

  const renderObject = (obj) => {
    if (obj && obj.summery.company_overview && obj.summery.company_overview['table-1']) {
      const companyInfo = obj.summery.company_overview['table-1'];
      const jobPostings = obj.summery.job_Posting['table-0'];
    if(msg === obj.summery.company_overview['table-1'].name){
        return (
          <div>
            <img src={companyInfo.logo} alt="" />
            {companyInfo.name}
            {obj.summery.company_overview['table-0']['대표자']}
            {obj.summery.company_overview['table-0']['설립일']}
            {obj.summery.company_overview['table-0']['기업규모']}
            {companyInfo.description}
            {companyInfo.numberOfEmployees}
            {companyInfo.address}
            {Object.keys(jobPostings).length}
          </div>
        );
      } else if (msg == '채용공고') {
        return (
          <div>
            {Object.entries(jobPostings).map(([key, value]) => (
              <div key={key}>
                {value[0]}
                <a href={value.link} target="_blank">{value.link}</a>
              </div>
            ))}
          </div>
        );
      } else {
        return('Chat GPT 연결 예정')
      }
    }
    
  };
  return (
    <div id="container">
      <div id="chat_container">
        <div id="current_info">{renderObject(company_info)}</div>
      </div>
    </div>
  );
}
