import React, { FC } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

//import AccordionSummary from '@mui/material/AccordionSummary';
//import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MuiAccordion from '@mui/material/Accordion';
import MuiAccordionSummary from '@mui/material/AccordionSummary';
import MuiAccordionDetails from '@mui/material/AccordionDetails';
import { withStyles } from '@mui/styles';
import { useSelector } from 'react-redux';
import { RootState } from 'src/redux/store';

import styles from './CertificateCardPage.module.scss';

function convertDate(inputFormat: string): string {
  function pad(s: number) {
    return s < 10 ? `0${s}` : s;
  }
  const d = new Date(inputFormat);
  return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('.');
}

const CertificateCardPage: FC = () => {
  const navigate = useNavigate();
  const { certID } = useParams();

  const cert = useSelector((state: RootState) =>
    state.test.test.filter((item) => item['id'] == certID),
  )[0];
  const { id, begin, end, longKey, algorithmKey} = cert;
  console.log({ begin});

  const prevPageClickHandler = () => {
    navigate(-1);
  };

  const Accordion = withStyles({
    root: {
      backgroundColor: '#353436',
      color: 'white',
      '&$expanded': {
        margin: 'auto',
      },
    },
    expanded: {},
  })(MuiAccordion);

  const AccordionSummary = withStyles({
    root: {
      backgroundColor: '#4B4A4C',
      borderRadius: '6px',
    },
    expanded: {
      borderBottomRightRadius: '0px',
      borderBottomLeftRadius: '0px',
    },
  })(MuiAccordionSummary);

  const AccordionDetails = withStyles({
    root: {
      backgroundColor: '#414042',
      borderBottomLeftRadius: '6px',
      borderBottomRightRadius: '6px',
    },
  })(MuiAccordionDetails);

  return (
    <>
      <div className={styles.certСardWrapper}>
        <button onClick={prevPageClickHandler} className={styles.prevPageBtn}>
          <ArrowBackIcon />
          Центр сертификации
        </button>

        <Accordion>
          <AccordionSummary
            expandIcon={<ExpandMoreIcon className={styles.icon} />}
            aria-controls="panel1a-content"
            id="panel1a-header"
          >
            Цепочка сертификатов
          </AccordionSummary>
          <AccordionDetails>Древовидная структура цепочки сертификатов</AccordionDetails>
        </Accordion>

        <table className={styles.certTable}>
          <tbody>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Издатель</td>
              <td className={styles.certTdValue}>{`Издатель${id}`}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Владелец</td>
              <td className={styles.certTdValue}>{`Владелец${id}`}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Организация</td>
              <td className={styles.certTdValue}>{`Организация${id}`}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Департамент</td>
              <td className={styles.certTdValue}>{`Департамент${id}`}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Действует с</td>
              <td className={styles.certTdValue}>{convertDate(begin)}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Действует по</td>
              <td className={styles.certTdValue}>{convertDate(end)}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Алгоритм ключа</td>
              <td className={styles.certTdValue}>{algorithmKey}</td>
            </tr>
            <tr className={styles.certTr}>
              <td className={styles.certTdName}>Длина ключа</td>
              <td className={styles.certTdValue}>{longKey}</td>
            </tr>
          </tbody>
        </table>

        <Accordion>
          <AccordionSummary
            expandIcon={<ExpandMoreIcon className={styles.icon} />}
            aria-controls="panel1a-content"
            id="panel1a-header"
          >
            Состав
          </AccordionSummary>
          <AccordionDetails>таб панель</AccordionDetails>
        </Accordion>
      </div>
    </>
  );
};

export default CertificateCardPage;
