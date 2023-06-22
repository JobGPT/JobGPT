import axios from 'axios'

const CATCH_API_URL="https://www.catch.co.kr/apiGuide/guide/openAPIGuide/apiCompList";
const CATCH_API_KEY="OKi0USF3nvPj8a7RqbTErJqAeUNEt0YnkpKixpoEB2QcQ";
// console.log(API_URL)
const api = axios.create({
    baseURL: CATCH_API_URL,
    params:{
        APIKey:CATCH_API_KEY
    }
})



export default api;