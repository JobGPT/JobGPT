import './NewChat.css';
import chatimg from '../assets/chatimg.svg';

import { useState } from 'react';
import { useStore } from '../store';

import deleteimg from '../assets/delete.svg';
import editimg from '../assets/edit.svg';
import cancleimg from '../assets/cancle.svg';
import checkimg from '../assets/check.svg';

export default function NewChat({ title }) {
  const [is_active, setActive] = useState(false);
  const [edit_active, setEdit] = useState(false);
  const [delete_active, setDelete] = useState(false);
  const [active, setBtnActive] = useState(false);
  const [editedTitle, setEditedTitle] = useState(title); // 추가된 상태

  const btnClick = useStore((store) => store.btnClick);
  const cancle_confirmClick = () => {
    setBtnActive(false);
    setDelete(false);
    setEdit(false);
  };
  const handleClick = () => {
    setActive(!is_active);
    if (!is_active) {
      setBtnActive(false);
    }
  };
  const confirmClick = useStore((store) => store.confirmClick);
  const img = useStore((store) => store.img);

  const handleChangeTitle = (event) => {
    setEditedTitle(event.target.value);
  };

  const handleClickEdit = () => {
    btnClick('edit');
    setBtnActive(!active);
    setEdit(!edit_active);
  };

  const handleClickDelete = () => {
    btnClick('delete');
    setBtnActive(!active);
    setDelete(!delete_active);
  };

  return (
    <div className="relative" style={{ height: 'auto', opacity: 1 }}>
      <a
        className={
          'flex py-3 px-3 items-center gap-3 relative rounded-md cursor-pointer break-all )} pr-14 )} bg-gray-800 hover:bg-gray-800 group' +
          (is_active ? ' active' : '')
        }
        onClick={(event) => {
          event.preventDefault();
          console.log(`is_active : ${is_active}`);
          handleClick();
        }}
      >
        <img src={active ? img : chatimg} />
        <div className="flex-1 text-ellipsis max-h-5 overflow-hidden break-all relative">
          {edit_active ? (
            <input
              type="text"
              value={editedTitle}
              onChange={handleChangeTitle}
              className="text-white bg-transparent outline-none"
            />
          ) : (
            <div className="flex-1 text-ellipsis max-h-5 overflow-hidden break-all relative">{title}</div>
          )}
        </div>
        <div
          className="absolute flex right-1 z-10 text-gray-300"
          style={is_active ? { visibility: 'visible' } : { display: 'none' }}
          onClick={(event) => event.stopPropagation()}
        >
          <button
            onClick={handleClickEdit}
            id="button-edit"
            style={(edit_active && active) || delete_active ? { display: 'none' } : { visibility: 'visible' }}
          >
            <img src={editimg} />
          </button>
          <button
            onClick={() => {
              if (edit_active) {
                confirmClick(editedTitle);
              } else {
                confirmClick(title);
              }
            }}
            style={(delete_active || edit_active) && active ? { visibility: 'visible' } : { display: 'none' }}
          >
            <img src={checkimg} />
          </button>
          <button
            onClick={handleClickDelete}
            id="button-delete"
            style={(delete_active && active) || edit_active ? { display: 'none' } : { visibility: 'visible' }}
          >
            <img src={deleteimg} />
          </button>
          <button
            onClick={cancle_confirmClick}
            style={(delete_active || edit_active) && active ? { visibility: 'visible' } : { display: 'none' }}
          >
            <img src={cancleimg} />
          </button>
        </div>
      </a>
    </div>
  );
}
