// src/api/Profile.js
import request from '@/utils/request3'

// 获取当前登录用户信息
export const getUserInfoService = () => request.get('/user/userInfo')

// 修改密码
export const updatePasswordService = (data) => request.patch('/user/updatePwd', data)