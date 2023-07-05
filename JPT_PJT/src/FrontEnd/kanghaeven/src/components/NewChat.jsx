import chatimg from '../assets/chatimg.svg';
import './NewChat.css';

import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useStore } from '../store';

import cancleimg from '../assets/cancle.svg';
import checkimg from '../assets/check.svg';
import deleteimg from '../assets/delete.svg';
import editimg from '../assets/edit.svg';

export default function NewChat({ title, id }) {
  console.log(title);
  console.log(id);
  const [is_active, setActive] = useState(false); // a 태그 활성화
  const [edit_active, setEdit] = useState(false);
  const [delete_active, setDelete] = useState(false);
  const [active, setBtnActive] = useState(false); // 아무 버튼이나 활성화 되있는 상태
  const [editedTitle, setEditedTitle] = useState(title); // 추가된 상태
  const navigate = useNavigate();

  const btnClick = useStore((store) => store.btnClick);
  const cancle_confirmClick = (event) => {
    event.preventDefault();
    setBtnActive(false);
    setDelete(false);
    setEdit(false);
    setEditedTitle(title);
  };
  const handleClick = () => {
    setActive(true);
  };
  const confirmClick = useStore((store) => store.confirmClick);

  const img = useStore((store) => store.img);

  const handleChangeTitle = (event) => {
    setEditedTitle(event.target.value);
  };

  const handleClickEdit = (event) => {
    event.preventDefault();
    btnClick('edit');
    setBtnActive(!active);
    setEdit(!edit_active);
  };

  const handleClickDelete = (event) => {
    event.preventDefault();
    console.log('delete', delete_active);
    btnClick('delete');
    setBtnActive(!active);
    setDelete(true);
  };

  const handleConfirmClick = (event) => {
    if (delete_active) {
      confirmClick(editedTitle, delete_active, edit_active, id, event);
      navigate('/mainpage');
      setDelete(false);
      setBtnActive(false);
    } else if (edit_active) {
      setBtnActive(false);
      setEdit(false);
      confirmClick(editedTitle, delete_active, edit_active, id, event);
    }
  };

  return (
    <>
      <Link
        to={`/mainpage/${id}`}
        id="box"
        className={
          'flex items-center gap-3 relative rounded-md cursor-pointer break-all )} pr-14 )} bg-gray-800 hover:bg-gray-800 group' +
          (is_active ? ' active' : '')
        }
        onClick={(event) => {
          console.log(`is_active : ${is_active}`);
          handleClick();
        }}
      >
        <img src={active ? img : chatimg} />
        <div id="chatTitle">
          {edit_active ? (
            <input
              type="text"
              value={editedTitle}
              onChange={handleChangeTitle}
              className="text-white bg-transparent outline-none"
              style={{ width: '100%' }}
            />
          ) : (
            <div id="chatTitle">{title}</div>
          )}
        </div>
        <div
          className="btns"
          style={
            is_active
              ? { visibility: 'visible', height: '15px', width: 'auto' }
              : { display: 'none' }
          }
          onClick={(event) => event.stopPropagation()}
        >
          <button
            onClick={(event) => handleClickEdit(event)}
            id="button-edit"
            style={
              (edit_active && active) || delete_active
                ? { display: 'none' }
                : { visibility: 'visible', height: '15px', width: '15px' }
            }
          >
            <img src={editimg} style={{ height: '15px', width: '15px' }} />
          </button>
          <button
            onClick={(event) => handleConfirmClick(event)}
            style={
              (delete_active || edit_active) && active
                ? { visibility: 'visible', height: '20px', width: '20px' }
                : { display: 'none' }
            }
          >
            <img src={checkimg} style={{ height: '15px', width: '15px' }} />
          </button>
          <button
            onClick={(event) => handleClickDelete(event)}
            id="button-delete"
            style={
              (delete_active && active) || edit_active
                ? { display: 'none' }
                : { visibility: 'visible', height: '15px', width: '15px' }
            }
          >
            <img src={deleteimg} style={{ height: '15px', width: '15px' }} />
          </button>
          <button
            onClick={(event) => cancle_confirmClick(event)}
            style={
              (delete_active || edit_active) && active
                ? { visibility: 'visible', height: '20px', width: '20px' }
                : { display: 'none' }
            }
          >
            <img src={cancleimg} style={{ height: '15px', width: '15px' }} />
          </button>
        </div>
      </Link>
    </>
  );
}
