import { create } from 'zustand';
import { fetchLoginUser, fetchLogoutUser, fetchSignupUser } from './api/auth';
import { fetchCreateChatbox, fetchDeleteChatbox, fetchSearchBox } from './api/chat';
import chatimg from './assets/chatimg.svg';

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
          console.log(info);
          fetchSearchBox(info)
            .then((res) => {
              console.log(res.data);
              set(() => ({
                chats: [res.chatbox],
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
    confirmClick: (newTitle, deleteActive, editActive, id, event) => {
      event.preventDefault();
      if (deleteActive) {
        const data = {
          id: id,
          accesstoken: useStore.getState().accesstoken,
          refreshtoken: useStore.getState().refreshtoken,
        }
        fetchDeleteChatbox(data)
        .then((res) => {
          console.log(res)
        })
      } else if (editActive) {
        set((store) => ({
          chats: store.chats.map((chat) => {
            if (chat.id === id) {
              return { title: newTitle, id: chat.id };
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
