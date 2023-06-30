import React from 'react';

import './JobPosting.css';

export default function JobPosting({jobPostings}) {
    return(
    <div id="job">
        <div id="job_top">
            <h3>채용공고</h3>
        </div>
        <table id='job_table'>
            {Object.entries(jobPostings).map(([key, value]) => (
            <tbody>
                <tr>
                    <td id="job_name">
                        <a href={value.link} target="_blank" key={key}><span>{value[0]}</span></a>
                    </td>
                    <td style={{color: '#999', fontSize: '12px'}}>
                        {value[2]}
                    </td>
                    <td style = {value[3] === '마감' ? {color: '#838383', fontSize: '12px'} : {color: '#f45757', fontSize: '12px'}}>
                        {value[3]}
                    </td>
                </tr>
            </tbody>
            ))}
        </table>
    </div>
    )
}