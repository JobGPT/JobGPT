import api from '../base.js'
import JSZip from 'jszip';
import convert from 'xml-js'
import cheerio from 'cheerio';

// 회사의 고유번호를 챙겨옴
const fetchCorpCode = async ({target}) =>{
    console.log(`${target}의 정보를 Dart 에서 수집합니다.`)
    try {
        const res = await api.get('/corpCode.xml', {
            responseType: 'arraybuffer' // ZIP데이터를 반환하기 때문에 이진데이터 처리
        });
        
        const zip = new JSZip();
        const data = await zip.loadAsync(res.data);
        const file = await data.file('CORPCODE.xml').async("string");

        let result_3 = convert.xml2json(file, { compact: true, spaces: 4 });
        let corpCodes = JSON.parse(result_3);

        let target_inform_dart = corpCodes.result.list.find(item => item.corp_name._text === target);
        return await target_inform_dart;

    } catch (err) {
        console.error(err);
        return null
    }
}

// 회사 공시정보 가져오기
const fetchDisclosInform  = async ({corp_code}) =>{
    let now = new Date();
    let end_date = now.toISOString().slice(0,10).replace(/-/g, "");  // "YYYYMMDD" 형식으로 변환
    
    let start_date = new Date();
    start_date.setFullYear(start_date.getFullYear() - 1);  // 1년 전으로 날짜 설정
    start_date = start_date.toISOString().slice(0,10).replace(/-/g, "");  // "YYYYMMDD" 형식으로 변환
    
    try {
        const res = await api.get('/list.json', {    
            params :{
                'corp_code' : corp_code,
                'bgn_de' : start_date,
                'end_de' : end_date,
                'last_reprt_at': 'Y',
                'pblntf_ty': 'A',
                'corp_cls' : 'Y'
            },
        });

        return await res.data.list;
    } catch (err) {
        console.error(err);
        return null
    }
}


const fetchDocumentData = async ({rcept_no}) => {
    try{
        const res = await api.get('/document.xml', {
            params: {
                'rcept_no' : rcept_no,
            },
            responseType: 'arraybuffer'
        });

        const zip = new JSZip();
        const data = await zip.loadAsync(res.data);
        const file = await data.file(rcept_no + '.xml').async("string");

        const $ = cheerio.load(file, { xmlMode: true });

        const document = $('DOCUMENT');
        const body = document.find('BODY');
        const sections = body.find('SECTION-1');
        const about_comps = sections.eq(1).find('SECTION-2');
        const contents_of_business = sections.eq(2).find('LIBRARY SECTION-2');
        const about_comp = about_comps.eq(0);
        const business_outline = contents_of_business.eq(0);
        const main_services = contents_of_business.eq(1);

        console.log('========')
        console.log('Dart 정보 수집 완료');
        console.log('========')
        return {
            'about_comp' : about_comp.text(),
            'business_outline' : business_outline.text(),
            'main_services' : main_services.text()
        }

    }catch (err) {
        console.error(err);
        return null
    }
}


export {
    fetchCorpCode,
    fetchDisclosInform,
    fetchDocumentData
}