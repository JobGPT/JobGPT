import { create } from 'zustand';
import chatimg from './assets/chatimg.svg';

const store = (set) => {
  let index = 0;
  return{
  chats: [],
  img: chatimg,
  showOffcanvas01: true,
  setButtonImage: (image) => set({ img: image }),
  addChat: (title) =>{
    const newChat = {title, index};
    index ++;
    set((store) => ({
      chats: [...store.chats, newChat],
    }))},
  btnClick: (id) => {
    const buttonElement = document.getElementById(`button-${id}`);
    const imgElement = buttonElement.querySelector('img');
    const imgSource = imgElement.getAttribute('src');
    console.log(imgSource);
    set(() => ({
      img: imgSource,
    }));
  },
  confirmClick: (title, newTitle, deleteActive, editActive, index) => {
    if (deleteActive) {
      set((store) => ({
        chats: store.chats.filter((chat) => chat.index !== index),
      }));
    } else if (editActive) {
      set((store) => ({
        chats: store.chats.map((chat) => {
          if (chat.title === title) {
            return { title: newTitle, index: chat.index};
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
  };
};

export const useStore = create(store, { name: 'store' });
