import { call, put, takeEvery } from 'redux-saga/effects';

import request from '../api/ApiClient';

import { getTestSuccess } from './testSlice';

interface CommentsData {
  id: number;
  postId: number;
  body: string;
  email: string;
  name: string;
}

function* workFetch() {
  try {
    const data: CommentsData = yield call(() => {
      return request('get', '/certificates/all/5').then((data) => {
        return data;
      });
    });
    yield put(getTestSuccess(data));
  } catch (error) {
    // тут потом тож напиши обработку ошибки
    console.log(error);
  }
}

function* testSaga() {
  yield takeEvery('test/getTestFetch', workFetch);
}

export default testSaga;
