import React from 'react';

import { Router } from '../../Router';

import styles from './Main.module.scss';

const Main = () => {
  return (
    <div className={styles.main}>
      <Router />
    </div>
  );
};

export default Main;
