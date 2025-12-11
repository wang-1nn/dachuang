import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/counter'
import { ElMessage } from 'element-plus'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/customer',
    name: 'customer',
    component: () => import('../views/CustomerDashboard.vue'),
    meta: { role: 'CUSTOMER' }
  },
  {
    path: '/merchant',
    name: 'merchant',
    component: () => import('../views/MerchantDashboard.vue'),
    meta: { role: 'MERCHANT' }
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('../views/AdminDashboard.vue'),
    meta: { role: 'ADMIN' }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/login'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

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

  if (to.meta.role && userStore.userInfo && userStore.userInfo.role !== to.meta.role) {
    ElMessage.warning('无权限访问该页面')
    return next('/login')
  }

  next()
})

export default router
