import axios from 'axios';

const loginUrl = 'http://localhost:8080/api/login';
const signupUrl = 'http://localhost:8080/api/signup';
const logoutUrl = 'http://localhost:8080/api/logout';

const fetchLoginUser = (data) => {
  return axios.post(loginUrl, data, {
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

const fetchLogoutUser = (data) => {
  try {
    console.log(data);
    return axios.get(logoutUrl, {
      headers: {
        Accesstoken: `Bearer ${data.accesstoken}`,
      },
    });
  } catch {
    return axios.get(logoutUrl, {
      headers: {
        Refreshtoken: `Bearer ${data.refreshtoken}`,
      },
    });
  }
};

export { fetchLoginUser, fetchSignupUser, fetchLogoutUser };
