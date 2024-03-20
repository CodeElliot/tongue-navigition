import {createRouter,createWebHistory} from 'vue-router'
// 导入路由页面
import ManageView from '@/views/manage/ManageView.vue'
import LoginView from '@/views/login/LoginView.vue'
import DishView from '@/views/dish/DishView.vue'
import EmployeeView from '@/views/employee/EmployeeView.vue'
import UserViewVue from '@/views/user/UserView.vue'
import ChartViewVue from '@/views/chart/ChartView.vue'
import MyInfoView from '@/views/employee/MyInfoView.vue'

const routes=[
    {path:'/login',component:LoginView},
    {path:'/',component:ManageView,redirect: "/dish",children:[
        {path:'/dish',component:DishView},
        {path:'/employee',component:EmployeeView},
        {path:'/employee/myinfo',component:MyInfoView},
        {path:'/user',component:UserViewVue},
        {path:'/chart',component:ChartViewVue},
    ]}
]
const router =createRouter({
    history:createWebHistory(),
    routes:routes
})
export default router