import { create } from 'zustand';
import chatimg from './assets/chatimg.svg';

const store = (set) => {
  let index = 0;

  return {
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
