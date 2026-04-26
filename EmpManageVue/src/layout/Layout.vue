<template>
  <div class="layout">
    <Header />
    <Sidebar />
    <div class="main-container">
      <AppMain />
    </div>
  </div>
</template>

<script setup>
import Header from './components/Header.vue'
import Sidebar from './components/Sidebar.vue'
import AppMain from './components/AppMain.vue'
</script>

<style scoped>
.layout {
  height: 100vh;
  width: 100vw;
  overflow: hidden;                 /* 禁止整个布局滚动 */
  position: relative;
}

.main-container {
  position: absolute;               /* 改成 absolute 定位 */
  top: 70px;                        /* 顶部高度 */
  left: 220px;                      /* 左侧菜单宽度 */
  right: 0;
  bottom: 0;
  transition: left 0.28s;
  overflow: hidden;                 /* 自己不滚 */
}

/* 菜单折叠时 */
.el-menu--collapse ~ .layout .main-container {
  left: 64px;
}
</style>