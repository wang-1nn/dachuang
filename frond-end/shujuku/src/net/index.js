import axios from 'axios'
import { ElMessage } from 'element-plus'

const defaultError = () => ElMessage.error('发生错误，请联系管理员。')
const defaultFailure = (message) => ElMessage.warning(message)

function getAuthToken() {
  const token = localStorage.getItem('authToken')
  if (token) {
    return `Bearer ${token}`
  }
  return ''
}

function post(url, data, success, failure = defaultFailure, error = defaultError, useJson = false) {
  const contentType = useJson ? 'application/json' : 'application/x-www-form-urlencoded'
  const requestData = useJson ? JSON.stringify(data) : new URLSearchParams(data).toString()

  return axios
    .post(url, requestData, {
      headers: {
        Authorization: getAuthToken(),
        'Content-Type': contentType
      },
      withCredentials: true
    })
    .then(({ data }) => {
      if (data.success) {
        success && success(data.message, data.data, data.status)
      } else {
        failure(data.message, data.data, data.status)
      }
      return data
    })
    .catch((err) => {
      error(err)
      throw err
    })
}

function get(url, data = null, success, failure = defaultFailure, error = defaultError) {
  const config = {
    withCredentials: true,
    params: data,
    headers: {
      Authorization: getAuthToken()
    }
  }

  return axios
    .get(url, config)
    .then(({ data }) => {
      if (data.success) success && success(data.message, data.data, data.status)
      else failure(data.message, data.data, data.status)
      return data
    })
    .catch((err) => {
      error(err)
      throw err
    })
}

function InternalGet(url, success, failure = defaultFailure, error = defaultError) {
  axios
    .get(url, {
      withCredentials: true
    })
    .then((response) => {
      success(response.data)
    })
    .catch(error)
}

export { get, post, InternalGet }
