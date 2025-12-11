import './assets/main.css'

import ElementPlus from 'element-plus'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import axios from "axios"
import App from './App.vue'
import router from './router'
import { useUserStore } from './stores/counter'
import { message } from 'ant-design-vue'

const app = createApp(App)
const pinia = createPinia()

// 配置 axios
axios.defaults.baseURL = 'http://localhost:8080' // 你的后端地址
// axios.defaults.baseURL = 'http://116.205.98.117'
axios.defaults.withCredentials = true

// 全局配置
app.config.globalProperties.$axios = axios
app.config.performance = true
app.config.unwrapInjectedRef = true

// 使用插件
app.use(pinia)
app.use(router)
app.use(Antd)
app.use(ElementPlus)

const userStore = useUserStore()

// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    // 如果用户已登录，添加用户信息到请求头
    if (userStore.isLoggedIn) {
      config.headers['Authorization'] = `Bearer ${localStorage.getItem('authToken')}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 添加响应拦截器
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response) {
      // 处理401错误（未授权）
      if (error.response.status === 401) {
        userStore.clearUser()
        router.push('/login')
        message.error('登录已过期，请重新登录')
      }
    }
    return Promise.reject(error)
  }
)

// 全局错误处理
app.config.errorHandler = (err, vm, info) => {
  console.error('全局错误:', err)
  message.error('操作出错，请重试')
}

// 挂载应用
app.mount('#app')
