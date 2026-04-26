import { createApp } from 'vue'//导入Vue
import ElementPlus from 'element-plus'//导入ElementPlus
import 'element-plus/dist/index.css'//导入ElementPlus的样式
import App from './App.vue'//导入App组件
import Cn from 'element-plus/dist/locale/zh-cn.js'//导入中文包
import router from './router'//导入路由
import { createPinia } from 'pinia' //导入pinia
import { createPersistedState } from 'pinia-persistedstate-plugin'
const app = createApp(App)//创建应用实例

app.use(router)//使用路由
app.use(ElementPlus, {locale: Cn})//使用ElementPlus

const persist = createPersistedState();
const pinia=createPinia();
pinia.use(persist);
app.use(pinia) // 使用Pinia

app.mount('#app')//控制html元素