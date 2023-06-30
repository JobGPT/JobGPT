import axios from 'axios'
// const API_URL = process.env.VUE_APP_API_URL
const API_URL ='https://opendart.fss.or.kr/api'
const crtfc_key = '6ff44cfbc9ab0514222ca2d18dc68814282e81c9'
// console.log(API_URL)
const api = axios.create({
    baseURL: API_URL,
    params: {
        crtfc_key: crtfc_key
    }
})

// // api token 설정 메소드 정의
// api.setToken = function(token){
//     this.defaults.headers.common['Authorization'] = `Token ${token}`
// }
// // api token 제거 메소드 정의
// api.clearToken = function(){
//     delete this.defaults.headers.common['Authorization']
// }

export default api;