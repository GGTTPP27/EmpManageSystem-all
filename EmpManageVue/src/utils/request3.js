import axios from "axios";
import { ElMessage } from 'element-plus'
import router from '@/router'
//const baseURL = 'http://localhost:8081';
const baseURL = '/api';
const instance = axios.create({ baseURL });

import { useTokenStore } from "@/stores/token.js";
// 请求拦截器
instance.interceptors.request.use(config => {
    //请求前的回调
    //添加token
    const tokenStore = useTokenStore();
    const token = tokenStore.token;
    //判断有没有token
    if (token) {
        config.headers.Authorization = tokenStore.token;
    }
    return config;
});


instance.interceptors.response.use(
    result => {
        return result;
    }, error => {
        if (error.response.status === 401) {
            ElMessage.error("请先登录");
            // 跳转登录页面
            router.push('/login');
            // 清除token
        } else {
            ElMessage.error("服务异常");
        }

        return Promise.reject(error);
    });

export default instance;