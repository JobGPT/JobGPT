import axios from 'axios';
const loginUrl = 'http://locahost:5173/login';
const signupUrl = 'http://locahost:5173/signup';


const fetchLoginUser = (data) => {
  return axios.post(loginUrl, data, {
    headers: {
      'Content-Type': 'application/json',
    }
  })     
}

const fetchSignupUser = (data) => {
  return axios.post(signupUrl, data, {
    headers: {
      'Content-Type': 'application/json',
    }
  })
}

export { fetchLoginUser, fetchSignupUser };