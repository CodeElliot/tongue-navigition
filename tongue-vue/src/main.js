import './assets/main.css'
import router from "@/router"
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import locale from "element-plus/dist/locale/zh-cn.js"
import {createPinia} from "pinia"
import {createPersistedState} from "pinia-persistedstate-plugin"
import * as echarts from 'echarts';

const app = createApp(App)
const pinia=createPinia()
//持久化插件
const persist=createPersistedState()
pinia.use(persist)
app.use(ElementPlus,{locale})
app.use(router)
app.use(pinia)
app.mount('#app')
