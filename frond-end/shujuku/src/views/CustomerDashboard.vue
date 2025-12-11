<template>
  <div class="page">
    <h2>消费者端</h2>
    <p>查看最新农产品并创建订单。</p>
    <a-button type="primary" @click="loadProducts">刷新商品</a-button>
    <a-list :data-source="products" bordered class="list">
      <template #renderItem="{ item }">
        <a-list-item>
          <div class="title">{{ item.name }} · ¥{{ item.price }}</div>
          <div class="desc">{{ item.description }}</div>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { get } from '@/net'

const products = ref([])

const loadProducts = () => {
  get('/api/products', null, (_, data) => {
    products.value = data || []
  })
}

onMounted(loadProducts)
</script>

<style scoped>
.page {
  padding: 24px;
}
.list {
  margin-top: 12px;
}
.title {
  font-weight: 600;
}
.desc {
  color: #999;
}
</style>
