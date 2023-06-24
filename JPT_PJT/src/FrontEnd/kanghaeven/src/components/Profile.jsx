import profileimg from '../assets/profile.svg';
import './Profile.css';

import { Disclosure } from '@headlessui/react';
import { ChevronUpIcon } from '@heroicons/react/20/solid';
import {Link} from 'react-router-dom';

import { useStore } from '../store.js';

export default function Profile() {
  const ClearConversation = useStore((store) => store.ClearConversation);
  return (
    <Disclosure>
      {({ open }) => (
        <>
          <div id="panel" style={{ color: 'white', marginBottom: '20px' }}>
            <Disclosure.Panel>
              <a onClick={ClearConversation}>Clear conversation</a>
              <Link to='/'>Log out</Link>
            </Disclosure.Panel>
          </div>
          <Disclosure.Button>
            <img src={profileimg} alt="profile" />
            nikname
            <ChevronUpIcon className={`${open ? 'rotate-180 transform' : ''} h-5 w-5 text-purple-500`} />
          </Disclosure.Button>
        </>
      )}
    </Disclosure>
  );
}
