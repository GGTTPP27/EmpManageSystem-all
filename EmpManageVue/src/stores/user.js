// src/stores/user.js
import { defineStore } from 'pinia'
import { ref } from "vue";

// 和 token.js 写法保持一致，开启持久化
export const useUserStore = defineStore('user', () => {
  // 定义用户信息（用ref，和你的token.js风格统一）
  const userId = ref('')
  const username = ref('')
  const createTime = ref('')
  const updateTime = ref('')

  // 设置用户信息（批量赋值）
  const setUserInfo = (userData) => {
    userId.value = userData.id || ''
    username.value = userData.username || ''
    createTime.value = userData.createTime || ''
    updateTime.value = userData.updateTime || ''
  }

  // 清空用户信息
  const removeUserInfo = () => {
    userId.value = ''
    username.value = ''
    createTime.value = ''
    updateTime.value = ''
  }

  // 返回状态和方法
  return {
    userId,
    username,
    createTime,
    updateTime,
    setUserInfo,
    removeUserInfo
  }
}, { persist: true }) // 开启持久化，和 token.js 保持一致