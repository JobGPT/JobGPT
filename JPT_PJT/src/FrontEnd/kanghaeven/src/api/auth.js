import axios from 'axios';
const loginUrl = 'http://locahost:8080/api/login';
const signupUrl = 'http://locahost:8080/api/signup';

const fetchLoginUser = (data) => {
  return axios.post(loginUrl, data, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
};

const fetchSignupUser = (data) => {
  return axios.post(signupUrl, data, {
    headers: {
      'Content-Type': 'application/json',
    },
    data,
  });
};

export { fetchLoginUser, fetchSignupUser };
