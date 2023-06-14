import { create } from 'zustand';
import chatimg from './assets/chatimg.svg';

const store = (set) => ({
  chats: [],
  active: false,
  img: chatimg,
  setButtonImage: (image) => set({ img: image }),
  addChat: (title) =>
    set((store) => ({
      chats: [...store.chats, { title }],
    })),
  btnClick: (id) => {
    const buttonElement = document.getElementById(`button-${id}`);
    const imgElement = buttonElement.querySelector('img');
    const imgSource = imgElement.getAttribute('src');
    console.log(imgSource);
    set(() => ({
      img: imgSource,
    }));
  },
  confirmClick: (title, newTitle, deleteActive, editActive) => {
    if (deleteActive) {
      set((store) => ({
        chats: store.chats.filter((chat) => chat.title !== title),
      }));
    } else if (editActive) {
      set((store) => ({
        chats: store.chats.map((chat) => {
          if (chat.title === title) {
            return { title: newTitle };
          }
          return chat;
        }),
      }));
    }
  },
});

export const useStore = create(store, { name: 'store' });
