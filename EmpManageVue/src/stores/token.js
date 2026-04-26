//定义store
import { defineStore } from 'pinia'
import {ref} from "vue";

/*
  参数一：唯一 名字
  参数二： 函数，函数内部定义状态的所以内容
*/
export const useTokenStore = defineStore('token',() => {
    // 定义状态
  //1.响应式变量  
  const token = ref('')
  //2.定义一个函数，修改token值
  const setToken = (newToken) => {
    token.value = newToken
  }
  //3.函数，移除token的值
  const removeToken = () => {
    token.value = ''
  }



  //4.返回状态
  return {
    token,
    setToken,
    removeToken
  } 
},{persist: true}// 开启数据持久化;
) 