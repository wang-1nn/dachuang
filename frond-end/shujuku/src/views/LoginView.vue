<template>
  <div class="login-page">
    <div class="panel">
      <h2>农产品销售平台</h2>
      <a-form layout="vertical" @finish="onSubmit">
        <a-form-item label="用户名" name="username" :rules="[{ required: true, message: '请输入用户名' }]">
          <a-input v-model:value="form.username" />
        </a-form-item>
        <a-form-item label="密码" name="password" :rules="[{ required: true, message: '请输入密码' }]">
          <a-input-password v-model:value="form.password" />
        </a-form-item>
        <a-form-item label="角色" name="role" :rules="[{ required: true, message: '请选择角色' }]">
          <a-select v-model:value="form.role" placeholder="选择登录端">
            <a-select-option value="CUSTOMER">消费者</a-select-option>
            <a-select-option value="MERCHANT">商家</a-select-option>
            <a-select-option value="ADMIN">管理员</a-select-option>
          </a-select>
        </a-form-item>
        <div class="actions">
          <a-button type="primary" html-type="submit" :loading="loading" block>登录 / 注册</a-button>
        </div>
      </a-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { post } from '@/net'
import { useUserStore } from '@/stores/counter'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const form = reactive({
  username: '',
  password: '',
  role: 'CUSTOMER'
})

const onSubmit = () => {
  loading.value = true
  post(
    '/api/auth/login',
    form,
    (_, data) => {
      localStorage.setItem('authToken', data.token)
      localStorage.setItem('userInfo', JSON.stringify(data.user))
      userStore.setUser(data.user)
      router.push(`/${data.user.role.toLowerCase()}`)
    },
    (message) => {
      // 如果登陆失败尝试注册
      post(
        '/api/auth/register',
        form,
        () => {
          router.push(`/${form.role.toLowerCase()}`)
        },
        () => {},
        () => {},
        true
      )
      console.warn(message)
    },
    () => {},
    true
  )
  loading.value = false
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f6ffed, #e6f7ff);
}

.panel {
  width: 360px;
  padding: 28px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.actions {
  margin-top: 12px;
}
</style>
