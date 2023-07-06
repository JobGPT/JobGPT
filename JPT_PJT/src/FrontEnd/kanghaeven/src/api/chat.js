import axios from 'axios';

const createUrl = 'http://localhost:8080/api/create/chatbox';
const deleteUrl = 'http://localhost:8080/api/deletebox';
const searchUrl = 'http://localhost:8080/api/searchbox';
const chatUrl = 'http://localhost:8080/api/create/chat';

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
  const info = { id: data.id };
  console.log(info);
  try {
    return axios.delete(deleteUrl, {
      headers: {
        Accesstoken: `Bearer ${data.accesstoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: info,
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

const fetchCreateChat = (data) => {
  const info = { talkboxId: data.talkboxId, talk: data.talk, title: data.title };
  try {
    return axios.post(chatUrl, info, {
      headers: {
        AccessToken: `Bearer ${data.accesstoken}`,
        'Content-Type': 'application/x-www-form-urlencoded',
      },

    });
  } catch {
    return axios.post(chatUrl, info ,{
      headers: {
        RefreshToken: `Bearer ${data.refreshtoken}`,
        'Content-type': 'application/x-www-form-urlencoded',
      },

    });
  }
};

export { fetchCreateChatbox, fetchDeleteChatbox, fetchSearchBox, fetchCreateChat };
