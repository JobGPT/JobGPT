import editimg from '../../assets/edit.svg';
import { useStore } from '../../store';

export default function EditButton({ id }) {
  const btnClick = useStore((store) => store.btnClick);
  const edit_active = useStore((store) => store.edit_active);
  const delete_active = useStore((store) => store.delete_active);
  const active = useStore((store) => store.active);
  const handleClick = () => {
    btnClick(id),
    useStore.setState((store) => ({
      edit_active: !store.edit_active
    }))
  };
  return (
    <button onClick={handleClick} id="button-edit" style={(edit_active && active) || delete_active ? { display: 'none' } : { visibility: 'visible' }}>
      <img src={editimg} />
    </button>
  );
}
