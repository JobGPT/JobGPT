import axios from 'axios';
const loginUrl = 'http://localhost:8080/api/login';
const signupUrl = 'http://localhost:8080/api/signup';

const fetchLoginUser = (data) => {
  const jsondata = JSON.stringify(data);
  console.log(typeof data);
  return axios.post(loginUrl, jsondata, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
};

const fetchSignupUser = (data) => {
  const formData = new URLSearchParams();
  for (const key in data) {
    formData.append(key, data[key]);
  }

  return axios.post(signupUrl, formData.toString(), {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    transformRequest: [(data) => data],
  });
};

export { fetchLoginUser, fetchSignupUser };
