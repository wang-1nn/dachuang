import { defineStore } from 'pinia'
import { get } from '@/net'

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: !!localStorage.getItem('authToken'),
    userInfo: localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : null
  }),
  
  getters: {
    // 获取用户角色
    isAdmin: (state) => state.userInfo?.role === 1,
    // 获取用户状态
    isActive: (state) => state.userInfo?.status === 0
  },

  actions: {
    // 初始化用户信息
    async initUser() {
      try {
        const response = await get('/api/auth/profile')
        if (response && response.data) {
          this.userInfo = response.data
          this.isLoggedIn = true
          localStorage.setItem('userInfo', JSON.stringify(response.data))
          localStorage.setItem('role', response.data.role)
          return response.data
        }
        throw new Error('获取用户信息失败')
      } catch (error) {
        console.error('初始化用户信息失败:', error)
        this.userInfo = null
        this.isLoggedIn = false
        localStorage.removeItem('userInfo')
        localStorage.removeItem('role')
        throw error
      }
    },

    // 设置用户信息
    setUser(userInfo) {
      this.userInfo = userInfo
      this.isLoggedIn = true
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
      if (userInfo.role !== undefined) {
        localStorage.setItem('role', userInfo.role)
      }
    },

    // 清除用户信息
    clearUser() {
      this.userInfo = null
      this.isLoggedIn = false
      localStorage.removeItem('userInfo')
      localStorage.removeItem('authToken')
      localStorage.removeItem('role')
      localStorage.removeItem('rememberedUsername')
    },

    // 新增 logout action
    logout() {
      this.clearUser();
      // 可以在这里添加其他登出逻辑，例如通知后端等
      // 但路由跳转已在 AdminLayout.vue 中处理
    }
  }
})