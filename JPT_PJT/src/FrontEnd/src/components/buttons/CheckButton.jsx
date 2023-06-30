import checkimg from '../../assets/check.svg';
import { useStore } from '../../store';

export default function CheckButton({ title }) {
  const active = useStore((store) => store.active);
  const delete_active = useStore((store) => store.delete_active);
  const edit_active = useStore((store) => store.edit_active);
  const confirmClick = useStore((store) => store.confirmClick);

  return (
    <button
      onClick={() => confirmClick(title)}
      style={(delete_active || edit_active) && active ? { visibility: 'visible' } : { display: 'none' }}
    >
      <img src={checkimg} />
    </button>
  );
}
