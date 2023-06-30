import profileimg from '../assets/profile.svg';
import './Profile.css';

import { Disclosure } from '@headlessui/react';
import { ChevronUpIcon } from '@heroicons/react/20/solid';
import { Link } from 'react-router-dom';

import { useStore } from '../store.js';

export default function Profile() {
  const ClearConversation = useStore((store) => store.ClearConversation);
  const logoutUser = useStore((store) => store.logoutUser);
  return (
    <Disclosure>
      {({ open }) => (
        <>
          <div id="panel" style={{ color: 'white', marginBottom: '1rem' }}>
            <Disclosure.Panel>
              <a id="profile_btn" onClick={ClearConversation}>
                Clear conversation
              </a>
              <Link
                onClick={logoutUser}
                to="/"
                style={{ textDecoration: 'none' }}
                id="logout_btn"
              >
                Log out
              </Link>
            </Disclosure.Panel>
          </div>
          <Disclosure.Button>
            <img src={profileimg} alt="profile" />
            <div className="nickname">nikname</div>
            <ChevronUpIcon
              className={`${open ? 'rotate-180 transform' : ''} h-5 w-5 text-purple-500`}
            />
          </Disclosure.Button>
        </>
      )}
    </Disclosure>
  );
}
