import React from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';

import store from './redux/store';
import Header from './layouts/Header';
import Sidebar from './layouts/Sidebar';
import Main from './layouts/Main';
import styles from './App.module.scss';
import { Router } from './Router';

export const App = () => {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Header />
        <div className={styles.container}>
          <Sidebar />
          <Main />
        </div>
      </BrowserRouter>
    </Provider>
  );
};
