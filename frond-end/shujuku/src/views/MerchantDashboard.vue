<template>
  <div class="page">
    <h2>商家端</h2>
    <p>管理自己的农产品并查看订单。</p>
    <a-button type="primary" @click="loadProducts">刷新商品</a-button>
    <a-button class="gap" @click="createDemo">新增示例商品</a-button>
    <a-list :data-source="products" bordered class="list">
      <template #renderItem="{ item }">
        <a-list-item>
          <div class="title">{{ item.name }} · 库存 {{ item.stock }}</div>
          <div class="desc">{{ item.description }}</div>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { get, post } from '@/net'

const products = ref([])

const loadProducts = () => {
  get('/api/products/merchant', null, (_, data) => {
    products.value = data || []
  })
}

const createDemo = () => {
  post(
    '/api/products',
    {
      name: '示例蔬菜',
      description: '便于联调的演示商品',
      price: 9.9,
      stock: 50,
      cover: ''
    },
    () => loadProducts(),
    () => {}
  )
}

onMounted(loadProducts)
</script>

<style scoped>
.page {
  padding: 24px;
}
.gap {
  margin-left: 8px;
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
