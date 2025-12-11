<template>
  <div class="page">
    <h2>管理员端</h2>
    <p>审核商家、查看用户列表。</p>
    <a-button type="primary" @click="loadUsers">刷新用户列表</a-button>
    <div class="grid">
      <div>
        <h3>商家</h3>
        <a-list :data-source="merchants" bordered>
          <template #renderItem="{ item }">
            <a-list-item>
              <div>{{ item.username }} · 状态 {{ item.status }}</div>
              <a-button size="small" type="link" @click="approve(item.id)">通过</a-button>
            </a-list-item>
          </template>
        </a-list>
      </div>
      <div>
        <h3>消费者</h3>
        <a-list :data-source="customers" bordered>
          <template #renderItem="{ item }">
            <a-list-item>{{ item.username }}</a-list-item>
          </template>
        </a-list>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { get, post } from '@/net'

const merchants = ref([])
const customers = ref([])

const loadUsers = () => {
  get('/api/admin/users', null, (_, data) => {
    merchants.value = data.merchants || []
    customers.value = data.customers || []
  })
}

const approve = (id) => {
  post(`/api/admin/users/${id}/status/0`, {}, () => loadUsers())
}

onMounted(loadUsers)
</script>

<style scoped>
.page {
  padding: 24px;
}
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 16px;
  margin-top: 12px;
}
</style>
