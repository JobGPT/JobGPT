import axios from 'axios'
import xml2js from 'xml2js'
const CATCH_API_URL="https://www.catch.co.kr/apiGuide/guide/openAPIGuide/apiCompList"
const CATCH_API_KEY="OKi0USF3nvPj8a7RqbTErJqAeUNEt0YnkpKixpoEB2QcQ"

const name = "Naver"
const {data} = await axios({
                            method:'get',
                            url:CATCH_API_URL,
                            params:{
                                Service:1,
                                CompName:`${name}`,
                                SortCode:1,
                                APIKey:CATCH_API_KEY
                            }
                        })


async function fetchAndParseXML(res_data) {
// API로부터 XML 데이터를 가져옵니다.
    return new Promise((resolve, reject) => {
        // XML 데이터를 JSON으로 변환합니다.
        xml2js.parseString(res_data, (err, result) => {
        if (err) {
            reject(err);
        } else {
            resolve(result);
        }
        });
    });
}

const CompanysConent = await fetchAndParseXML(data);
console.log(CompanysConent.Data.Companys[0].Company[0])