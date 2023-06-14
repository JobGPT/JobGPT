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
  confirmClick: (title) => {
    set((store) => ({
      chats: store.chats.filter((chat) => chat.title !== title),
    }));
  },
});

export const useStore = create(store, { name: 'store' });
