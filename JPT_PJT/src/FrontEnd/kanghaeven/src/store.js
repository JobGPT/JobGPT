import { create } from 'zustand';
import chatimg from './assets/chatimg.svg';
import { fetchLoginUser, fetchSignupUser } from './api/auth';

const store = (set) => {
  let index = 0;

  return {
    username: '',
    usernameMessage: '',
    password: '',
    passwordMessage: '',
    confirmPassword: '',
    confirmPasswordMessage: '',
    email: '',
    emailMessage: '',
    accesstoken: '',
    refreshtoken: '',
    setUsername: (username) => set({ username }),
    setUsernameMessage: (usernameMessage) => set({ usernameMessage }),
    setPassword: (password) => set({ password }),
    setPasswordMessage: (passwordMessage) => set({ passwordMessage }),
    setConfirmPassword: (confirmPassword) => set({ confirmPassword }),
    setConfirmPasswordMessage: (confirmPasswordMessage) =>
      set({ confirmPasswordMessage }),
    setEmail: (email) => set({ email }),
    setEmailMessage: (emailMessage) => set({ emailMessage }),
    loginUser: async () => {
      try {
        console.log('Username:', useStore.getState().username);
        console.log('Password:', useStore.getState().password);
        const data = {
          username: useStore.getState().username,
          password: useStore.getState().password,
        };
        fetchLoginUser(data)
          .then((res) => {
            console.log(res.config.method);
            if (res.config.method === 'post') {
              // AccessToken 값 저장
              const accessToken = res.headers.accesstoken;
              useStore.setState({ accesstoken: accessToken });

              // RefreshToken 값 저장
              const refreshToken = res.headers['refreshtoken'];
              useStore.setState({ refreshtoken: refreshToken });
              return true;
            }
          })
          .catch((err) => {
            console.log(err);
            return false;
          });
      } catch (error) {
        console.log(error);
        return true;
      }
    },
    signupUser: async () => {
      try {
        console.log('Username:', useStore.getState().username);
        console.log('Password:', useStore.getState().password);
        console.log('Email:', useStore.getState().email);
        const data = {
          username: useStore.getState().username,
          password: useStore.getState().password,
          email: useStore.getState().email,
        };
        fetchSignupUser(data).then((res) => {
          console.log(res.data);
          return true;
        });
      } catch (error) {
        console.log(error);
        return true;
      }
    },

    chats: [],
    img: chatimg,
    showOffcanvas01: true,
    sendmessage: [],
    company_info: [],
    setButtonImage: (image) => set({ img: image }),
    addChat: (title) => {
      const newChat = { title, index };
      index++;
      set((store) => ({
        chats: [...store.chats, newChat],
      }));
    },
    btnClick: (id) => {
      const buttonElement = document.getElementById(`button-${id}`);
      const imgElement = buttonElement.querySelector('img');
      const imgSource = imgElement.getAttribute('src');
      set(() => ({
        img: imgSource,
      }));
    },
    confirmClick: (newTitle, deleteActive, editActive, index, event) => {
      event.preventDefault();
      if (deleteActive) {
        set((store) => ({
          chats: store.chats.filter((chat) => chat.index !== index),
        }));
      } else if (editActive) {
        set((store) => ({
          chats: store.chats.map((chat) => {
            if (chat.index === index) {
              return { title: newTitle, index: chat.index };
            }
            return chat;
          }),
        }));
      }
    },
    handleToggleOffcanvas01: () => {
      set((store) => ({
        showOffcanvas01: !store.showOffcanvas01,
      }));
    },
    ClearConversation: () => {
      set(() => ({
        chats: [],
      }));
    },
    addMessage: (message) => {
      set((state) => ({
        sendmessage: [...state.sendmessage, message],
      }));
    },
    save_company_info: (info) => {
      set(() => ({
        company_info: info,
      }));
    },
  };
};

export const useStore = create(store, { name: 'store' });
