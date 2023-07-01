import { create } from 'zustand';
import chatimg from './assets/chatimg.svg';
import { fetchLoginUser, fetchSignupUser, fetchLogoutUser } from './api/auth';
import { fetchCreateChatbox, fetchDeleteChatbox, fetchSearchBox } from './api/chat';
import { useState } from 'react';
import { Axios, AxiosError } from 'axios';

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
    setDataError: (error) => set({ error }),
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
            console.log(err.response.data);
            console.log(err.response.status);
            if (error.response) {
              setDataError({ data: error.response.data, status: error.response.status })
            };
            return false;
          });
      } catch (error) {
        console.log(error);
        return true;
      }
    },
    logoutUser: async () => {
      const data = {
        accesstoken: useStore.getState().accesstoken,
        refreshtoken: useStore.getState().refreshtoken,
      };
      fetchLogoutUser(data)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
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
    addChat2: (title) => {
      const data = {
        title: title,
        accesstoken: useStore.getState().accesstoken,
        refreshtoken: useStore.getState().refreshtoken,
      };
      console.log(data);
      fetchCreateChatbox(data)
        .then((res) => {
          console.log(res);
          const info = {
            accesstoken: useStore.getState().accesstoken,
            refreshtoken: useStore.getState().refreshtoken,
          };
          fetchSearchBox(info)
            .then((res) => {
              console.log(res.data);
              set((store) => ({
                chats: [...store.chats, res.data],
              }));
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch((err) => {
          console.log(err);
        });
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
