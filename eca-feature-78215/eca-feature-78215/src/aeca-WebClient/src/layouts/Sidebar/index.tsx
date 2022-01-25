import React from 'react';
import DashboardIcon from '@mui/icons-material/Dashboard';
import StorageIcon from '@mui/icons-material/Storage';
import DashboardTwoToneIcon from '@mui/icons-material/DashboardTwoTone';
import TextSnippetIcon from '@mui/icons-material/TextSnippet';
import VerifiedUserIcon from '@mui/icons-material/VerifiedUser';
import GridViewIcon from '@mui/icons-material/GridView';
import SecurityIcon from '@mui/icons-material/Security';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from 'src/redux/store';
import { NavLink } from 'react-router-dom';

import { changeMenuStatus } from '../../redux/testSlice';

import { Router } from './../../Router';
import styles from './Sidebar.module.scss';

const Item = (props: any) => {
  const Icon = props.icon;
  const openClass = props.isOpen ? '' : styles.hideLink;
  const checkActive: any = ({ isActive }: any) => (isActive ? styles.test2 : styles.link);
  return (
    <>
      <NavLink to={props.path} className={checkActive}>
        <div className={styles.sidebar__item}>
          <Icon fontSize="medium" className={styles.icon} />
          <span className={`${styles.test} ${openClass}`}>{props.displayName}</span>
        </div>
      </NavLink>
    </>
  );
};

const itemdata = [
  {
    displayName: 'Панель управления',
    path: '/',
    icon: DashboardTwoToneIcon,
  },
  {
    displayName: 'Центр сертификации',
    path: '/ca',
    icon: SecurityIcon,
  },
  {
    displayName: 'Доменная инфраструктура',
    path: '/domain',
    icon: StorageIcon,
  },
  {
    displayName: 'Субъекты аутентификации',
    path: '/subjects',
    icon: GridViewIcon,
  },
  {
    displayName: 'Журнал аудита',
    path: '/logs',
    icon: TextSnippetIcon,
  },
];

const Sidebar = () => {
  const isOpened = useSelector((state: RootState) => state.test.isOpened);
  const dispatch = useDispatch();

  const st = isOpened ? styles.menuOpen : styles.menuClosed;
  const IconType = isOpened ? ChevronLeftIcon : ChevronRightIcon;

  const handler = () => {
    dispatch(changeMenuStatus());
    console.log('test');
  };

  return (
    <>
      <div className={`${styles.sidebar} ${st}`}>
        <div>
          {itemdata.map((element, index) => {
            return (
              <Item
                key={index}
                path={element.path}
                displayName={element['displayName']}
                icon={element['icon']}
                isOpen={isOpened}
              />
            );
          })}
        </div>

        <div className={styles.menuToggleWrapper}>
          <button className={styles.menuToggle} onClick={handler}>
            <IconType fontSize="large" />
          </button>
        </div>
      </div>
    </>
  );
};

export default Sidebar;
