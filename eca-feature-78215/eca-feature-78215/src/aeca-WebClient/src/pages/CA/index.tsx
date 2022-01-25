import { ConstructionOutlined } from '@mui/icons-material';
import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { NavigateOptions, useNavigate, NavLink } from 'react-router-dom';
import { RootState } from 'src/redux/store';

import { getTestFetch } from '../../redux/testSlice';

import styles from './CA.module.scss';

function convertDate(inputFormat: string): string {
  function pad(s: number) {
    return s < 10 ? `0${s}` : s;
  }
  const d = new Date(inputFormat);
  return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('.');
}

function getStatus(status: string) {
  if (status == 'CERT_ACTIVE') return 'Активирован';
  else return 'Отозван';
}

export const AecaUIPage = () => {
  const test = useSelector((state: RootState) => state.test);
  const dispatch = useDispatch();
  const history = useNavigate();

  useEffect(() => {
    dispatch(getTestFetch());
  }, [dispatch]);

  console.log(test);

  return (
    <>
      <table className={styles.table}>
        <thead className={styles.th}>
          <tr>
            <th className={styles.thleft}>Издатель</th>
            <th>Владелец</th>
            <th>Действует с</th>
            <th>Действует по</th>
            <th>Алгоритм ключа</th>
            <th>Длина ключа</th>
            <th>Состояние</th>
          </tr>
        </thead>
        <tbody>
          {test.test.map((item, i) => {
            let x = '';
            if (getStatus(item['status']) == 'Активирован') x = styles.active;
            return (
              <tr
                className={styles.tr}
                key={item['id']}
                onClick={() => history(`/certificate/${item['id']}`)}
              >
                <td className={styles.td}>{`Издатель${item['id']}`}</td>
                <td className={styles.td}>{`Владелец${item['id']}`}</td>
                <td className={styles.td}>{convertDate(item['begin'])}</td>
                <td className={styles.td}>{convertDate(item['end'])}</td>
                <td className={styles.td}>{item['algorithmKey']}</td>
                <td className={styles.td}>{item['longKey']}</td>
                <td className={`${styles.td} ${x}`}>{getStatus(item['status'])}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </>
  );
};
