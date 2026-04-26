<!-- src/layout/components/Header.vue -->
<script lang="ts" setup>
import { ElMessageBox } from 'element-plus'
const logout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', { type: 'warning' })
    .then(() => {
      // 清空 token，跳转登录页
      localStorage.clear()
      location.href = '/login'
    })
}
</script>

<template>
  <div class="header">
    <div class="logo">
      
      <img src="@/assets/try1.png" alt="logo" style="width: 100px; height: 60px; margin-right: 10px;" />
      <span>员工人事调动管理系统</span>
    </div>
    <div class="user-info">
      <el-avatar :src="'https://cube.elemecdn.com/0/88/03b0d39583f48206768a87e7ce7b9jpeg.jpeg'" />
      <span style="margin-left: 10px; color: #fff">Admin</span>
      <el-button link type="primary" @click="logout" style="margin-left: 20px">退出登录</el-button>
    </div>
  </div>
</template>

<style scoped>
.header {
  height: 70px;
  background: linear-gradient(90deg, #440268, #0131a0);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1002;
}
.logo {
  font-size: 22px;
  font-weight: bold;
  display: flex;
  align-items: center;
}
.user-info {
  display: flex;
  align-items: center;
}
</style>