import api from '../base.js'
import convert from 'xml-js'


const get_summery = async (name) =>{
    console.log(`${name}의 정보를 Catch 에서 수집합니다.`)
    try {
        const {data} = await api({
            method:'get',
            params:{
                Service:1,
                CompName:`${name}`,
                SortCode:1,
            }
        });
        let result = convert.xml2json(data, { compact: true, spaces: 4 });

        return JSON.parse(result)
    } catch (err) {
        console.error(err);
        return null
    }
}

export {get_summery}