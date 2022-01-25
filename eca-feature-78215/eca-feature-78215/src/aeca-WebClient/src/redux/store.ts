import createSagaMiddleware from '@redux-saga/core';
import { configureStore } from '@reduxjs/toolkit';

import testReducer from './testSlice';
import testSaga from './testSaga';

const saga = createSagaMiddleware();
const store = configureStore({
  reducer: {
    test: testReducer,
  },
  middleware: [saga],
});
saga.run(testSaga);

export default store;
export type RootState = ReturnType<typeof store.getState>;
