import deleteimg from '../../assets/delete.svg';
import './Buttons.css';
import { useStore } from '../../store';

export default function DeleteButton({ id }) {
  const btnClick = useStore((store) => store.btnClick);
  const delete_active = useStore((store) => store.delete_active);
  const edit_active = useStore((store) => store.edit_active);
  const active = useStore((store) => store.active);
  const handleClick = () => {
    btnClick(id);
    useStore.setState((store) => ({
      delete_active: !store.delete_active,
    }));
  };
  return (
    <button
      onClick={handleClick}
      id="button-delete"
      style={(delete_active && active) || edit_active ? { display: 'none' } : { visibility: 'visible' }}
    >
      <img src={deleteimg} />
    </button>
  );
}
