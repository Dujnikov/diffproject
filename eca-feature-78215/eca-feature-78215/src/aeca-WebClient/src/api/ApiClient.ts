import axios, { Method } from 'axios';

// /**
//  * @typedef Response
//  * @type {object}
//  */

// /**
//  * @typedef ErrorResponse
//  * @type {object}
//  */

// /**
//  * @typedef {{
//  * data: object,
//  * before: () => void,
//  * success: (response: object) => void,
//  * error: (error: object) => void,
//  * headers: object,
//  * baseURL: string,
//  * }} Options
//  */

// /** Request to API.
//  * @param {string} method
//  * @param {string} url
//  * @param {Options} options
//  * @return {Promise<Response | ErrorResponse>}
//  */

type Response = object;

type Error = object;

export interface Options {
  data?: object;
  before?: () => void;
  success?: (response: Response) => void;
  error?: (error: Error) => void;
  headers?: object;
  baseURL?: string;
}

export const BASE_API = 'http://localhost:8080'; //это желательно вынести в переменную окружения

export const apiClient = axios.create({
  baseURL: BASE_API,
});

export default function request(
  method: Method,
  url: string,
  options: Options = {},
): Promise<Response> {
  const params = {};
  const { data = {}, headers = {}, baseURL = BASE_API } = options;

  return axios({
    method,
    baseURL,
    url,
    headers: { ...headers },
    params,
    data: method === 'get' ? {} : data,
  })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      return Promise.reject(
        (error.response && error.response.data) || {
          message: 'Что-то пошло не так, попробуйте снова.',
        },
      );
    });
}
