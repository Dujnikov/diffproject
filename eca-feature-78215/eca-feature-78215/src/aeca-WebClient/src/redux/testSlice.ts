import { createSlice } from '@reduxjs/toolkit';

export const testSlice = createSlice({
  name: 'test',
  initialState: {
    isLoading: false,
    isOpened: true,
    test: [],
  },
  reducers: {
    getTestFetch: (state) => {
      state.isLoading = true;
    },
    getTestSuccess: (state, action) => {
      state.test = action.payload;
      state.isLoading = false;
    },
    getTestFailure: (state) => {
      state.isLoading = false;
    },
    changeMenuStatus: (state) => {
      state.isOpened = !state.isOpened;
    },
  },
});

export const { getTestFetch, getTestSuccess, getTestFailure, changeMenuStatus } = testSlice.actions;
export default testSlice.reducer;
