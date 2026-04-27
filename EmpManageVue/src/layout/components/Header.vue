<script lang="ts" setup>
import { ElMessageBox } from 'element-plus'
// 导入你的 token 仓库（原有，无需修改）
import { useTokenStore } from '@/stores/token.js'
// 导入新增的 user 仓库（和 token.js 同级）
import { useUserStore } from '@/stores/user.js'
// 用 storeToRefs 保证用户名响应式更新（关键）
import { storeToRefs } from 'pinia'

// 获取仓库实例
const tokenStore = useTokenStore()
const userStore = useUserStore()
// 响应式获取用户名（避免页面不更新）
const { username } = storeToRefs(userStore)

const logout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', { type: 'warning' })
    .then(() => {
      tokenStore.removeToken()
      userStore.removeUserInfo()
      //跳转登录页
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
      <!-- 已完全删除用户头像标签，无任何残留 -->
      <span style="margin-left: 10px; color: #fff">{{ username || 'Admin' }}</span>
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
