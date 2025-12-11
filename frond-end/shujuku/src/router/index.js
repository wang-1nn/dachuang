import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/counter'
import { ElMessage } from 'element-plus'

const routes = [
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

// 路由守卫
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const token = localStorage.getItem('authToken')

  if (!userStore.userInfo && token) {
    try {
      await userStore.initUser()
    } catch (error) {
      localStorage.removeItem('authToken')
    }
  }



  next()
})

// 路由后置钩子
router.afterEach((to, from) => {
  if (to.path !== from.path) {
    const app = document.getElementById('app')
    if (app) {
      app.style.display = 'none'
      setTimeout(() => {
        app.style.display = ''
      }, 0)
    }
  }
})

export default router
