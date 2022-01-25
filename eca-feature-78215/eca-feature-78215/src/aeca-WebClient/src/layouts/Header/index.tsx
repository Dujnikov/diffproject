import React, { FC } from 'react';

import logo from './logo.png';
import user from './user.png';
import styles from './Header.module.scss';

const Header: FC = () => {
  return (
    <>
      <div className={styles.header}>
        <div className={styles.logo}>
          <img src={logo} />
          Aladdin ECA
        </div>
        <div className={styles.user}>
          <img src={user} />
        </div>
      </div>
    </>
  );
};

export default Header;
