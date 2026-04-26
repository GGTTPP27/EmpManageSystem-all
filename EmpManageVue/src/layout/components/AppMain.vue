<template>
  <section class="app-main">
    <router-view v-slot="{ Component }">
      <component :is="Component" />
    </router-view>
  </section>
</template>

<script setup>
// 无需任何逻辑
</script>

<style scoped>
.app-main {
  height: 100%;
  width: 100%;
  padding: 20px;
  overflow-y: auto;     /* 关键：只在这里出现垂直滚动条 */
  overflow-x: hidden;
  background: #f0f2f5;
  box-sizing: border-box;
}

/* 美化滚动条（可选） */
.app-main::-webkit-scrollbar {
  width: 10px;
}
.app-main::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}
.app-main::-webkit-scrollbar-thumb {
  background: #c0c4cc;
  border-radius: 10px;
}
.app-main::-webkit-scrollbar-thumb:hover {
  background: #a8aabb;
}
</style>