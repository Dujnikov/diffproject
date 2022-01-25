import React, { FC } from 'react';
import { Route, Routes } from 'react-router-dom';

import { AecaUIPage } from './pages/CA';
import CertificateCardPage from './pages/CertificateCardPage';
import { HomePage } from './pages/home';

export const Router: FC = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/ca" element={<AecaUIPage />} />
      <Route path="/certificate/:certID" element={<CertificateCardPage />} />
    </Routes>
  );
};
