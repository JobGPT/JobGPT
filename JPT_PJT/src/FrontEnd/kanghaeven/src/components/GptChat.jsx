import React from 'react';
import { useStore } from '../store.js';


import './GptChat.css';

export default function GptChat({msg}) {  
  const company_info = useStore((store) => store.company_info);
  const current_msg = msg.split(/은|는|이|가|을|를|에/)[0];
  const renderObject = (obj) => {
    if (obj && obj.summery.company_overview && obj.summery.company_overview['table-1']) {
      const companyInfo = obj.summery.company_overview['table-1'];
      const jobPostings = obj.summery.job_Posting['table-0'];
      const des = companyInfo.description.replace(/\n/g, '<br>');
      const comp_page = 'https://' + companyInfo['sameAs']
    if(current_msg == obj.summery.company_overview['table-1'].name){
        return (
          <div>
            <div style={{display: 'flex'}}>
              <img src={companyInfo.logo} />
              <h3>{companyInfo.name}</h3>
              <span>{companyInfo.makesOffer }</span>
              <a href={comp_page} target='_blank'>{companyInfo['sameAs']}</a>
            </div> {/* 정리 필요 */}
            <table id="info_container">
              <tbody>
              <tr>
                <td style={{ backgroundColor: 'lightgray'}}>
                  기업명
                </td>
                <td>{companyInfo.name}</td>
                <td style={{ backgroundColor: 'lightgray'}}>
                설립일
                </td>
                <td>{obj.summery.company_overview['table-0']['설립일']}</td>
              </tr>
              <tr>
                <td style={{ backgroundColor: 'lightgray'}}>
                대표자
                </td>
                <td>{obj.summery.company_overview['table-0']['대표자']}</td>
                <td style={{ backgroundColor: 'lightgray'}}>
                  사원 수
                </td>
                <td>{companyInfo.numberOfEmployees}명</td>
              </tr>
              <tr>
                <td style={{ backgroundColor: 'lightgray'}}>
                기업규모
                </td>
                <td>{obj.summery.company_overview['table-0']['기업규모']}</td>
                <td style={{ backgroundColor: 'lightgray'}}>채용공고 수</td>
                <td>{Object.keys(jobPostings).length}</td>
              </tr>
              </ tbody>
            </table>
            <br />
            <div id="description_box">
            <span style={{ fontWeight: 'bold', padding: '5px'}}>{companyInfo.name}?</span>
            <div dangerouslySetInnerHTML={{ __html: des }} id="description"></div>
            </div>
            <div>{companyInfo['address']}</div>
          </div>
        );
      } else if (current_msg == '채용공고') {
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
