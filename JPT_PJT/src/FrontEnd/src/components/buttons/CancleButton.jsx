import cancleimg from '../../assets/cancle.svg';
import { useStore } from '../../store';

export default function CancleButton() {
  const active = useStore((store) => store.active);
  const delete_active = useStore((store) => store.delete_active);
  const edit_active = useStore((store) => store.edit_active);
  const cancle_confirmClick = useStore((store) => store.cancle_confirmClick);
  return (
    <button onClick={cancle_confirmClick} style={(delete_active || edit_active) && active ? { visibility: 'visible' } : { display: 'none' }}>
      <img src={cancleimg} />
    </button>
  );
}
