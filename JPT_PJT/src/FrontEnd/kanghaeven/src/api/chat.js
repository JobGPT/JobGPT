import axios from 'axios';

const createUrl = 'http://localhost:8080/api/create/chatbox';
const deleteUrl = 'http://localhost:8080/api/deletebox';
const searchUrl = 'http://localhost:8080/api/searchbox';

const fetchCreateChatbox = (info) => {
  const data = { title: info.title };
  console.log(info);
  try {
    return axios.post(createUrl, data, {
      headers: {
        AccessToken: `Bearer ${info.accesstoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
  } catch {
    return axios.post(createUrl, data, {
      headers: {
        RefreshToken: `Bearer ${info.refreshtoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
  }
};

const fetchDeleteChatbox = (data) => {
  try {
    return axios.delete(deleteUrl, data.id, {
      headers: {
        Accesstoken: `Bearer ${data.accesstoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
  } catch {
    return axios.delete(deleteUrl, data.id, {
      headers: {
        RefreshToken: `Bearer ${data.refreshtoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
  }
};

const fetchSearchBox = (data) => {
  console.log(data);
  try {
    return axios.get(searchUrl, {
      headers: {
        AccessToken: `Bearer ${data.accesstoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
  } catch {
    return axios.get(searchUrl, {
      headers: {
        RefreshToken: `Bearer ${data.refreshtoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
  }
};

export { fetchCreateChatbox, fetchDeleteChatbox, fetchSearchBox };

