<!-- src/layout/components/Sidebar.vue -->
<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  User,
  Avatar,
  OfficeBuilding,
  SwitchFilled,
  Menu as IconMenu
} from '@element-plus/icons-vue'

const router = useRouter()
const isCollapse = ref(false)

const menuItems = [
  { path: '/profile', name: '管理员信息', icon: User },
  
  { path: '/employee', name: '员工信息管理', icon: Avatar },
  { path: '/retire', name: '离职退休管理', icon: SwitchFilled },
  { path: '/transfer', name: '调度信息管理', icon: SwitchFilled },
  { path: '/department', name: '部门信息统计', icon: OfficeBuilding },
]
</script>

<template>
  <el-menu
    :default-active="$route.path"
    class="sidebar-menu"
    :collapse="isCollapse"
    background-color="#001529"
    text-color="#bfc3c9"
    active-text-color="#fff"
    router
  >
    <div class="collapse-btn" @click="isCollapse = !isCollapse">
      <el-icon :size="20"><IconMenu /></el-icon>
    </div>

    <el-menu-item v-for="item in menuItems" :key="item.path" :index="item.path">
      <el-icon><component :is="item.icon" /></el-icon>
      <template #title>{{ item.name }}</template>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>
.sidebar-menu {
  height: 100vh;
  border-right: none;
  position: fixed;
  top: 70px;
  left: 0;
  bottom: 0;
  z-index: 1001;
  transition: width 0.28s;
}
.sidebar-menu:not(.el-menu--collapse) {
  width: 220px;
}
.sidebar-menu.el-menu--collapse {
  width: 64px;
}
.collapse-btn {
  height: 50px;
  line-height: 50px;
  text-align: center;
  background: linear-gradient(45deg, #440268, #0131a0);
  color: #fff;
  cursor: pointer;
}
</style>