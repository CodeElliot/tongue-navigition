<script setup>
import {
    Document,
    Menu as IconMenu,
    Location,
    User,
    Avatar,
    CreditCard
} from '@element-plus/icons-vue'
import avatar from "@/assets/default.png"
import { ElMessage } from 'element-plus'
import { useEmployeeInfoStore } from "@/stores/employeeInfo.js"
import {useTokenStore} from "@/stores/token.js"
import router from '@/router'
const useEmployeeInfo = useEmployeeInfoStore()
const useToken=useTokenStore()
const handleCommand = (command) => {
    if(command=='修改信息'){
        router.push('/employee/myInfo')
    }else  if(command=='修改密码'){
        router.push('/employee/myInfo')
    }else if(command=='退出登录'){
        router.push('/login')
        useToken.removeToken()
        useEmployeeInfo.removeInfo()
        ElMessage.success("退出登录")
    }
}
</script>

<template style="height:100%">
    <div class="common-layout" style="height:100%">
        <el-container style="height:100%">
            <el-container style="height:100%">
                <el-aside width="200px" style="background-color:#545c64;text-align:center;color:#fff;height:100%">
                    <h2 class="mb-2">舌尖导航</h2>
                    <el-menu active-text-color="#ffd04b" background-color="#545c64" text-color="#fff" router>
                        <el-menu-item index="/chart">
                            <el-icon><icon-menu /></el-icon>
                            <span>数据统计</span>
                        </el-menu-item>
                        <el-menu-item index="/dish">
                            <el-icon>
                                <document />
                            </el-icon>
                            <span>菜品管理</span>
                        </el-menu-item>
                        <el-sub-menu>
                            <template #title>
                                <el-icon>
                                    <el-icon>
                                        <User />
                                    </el-icon>
                                </el-icon>员工管理
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="/employee"><el-icon>
                                        <Avatar />
                                    </el-icon>全体员工</el-menu-item>
                                <el-menu-item index="/employee/myinfo"><el-icon>
                                        <CreditCard />
                                    </el-icon>个人信息</el-menu-item>
                            </el-menu-item-group>
                        </el-sub-menu>
                        <el-menu-item index="/user">
                            <el-icon>
                                <User />
                            </el-icon>
                            <span>用户管理</span>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <el-container>
                    <el-header height="30px" style="margin-top: 5px;">
                        <div style="width:50px;display:float;float:left">{{ useEmployeeInfo.info.ename }}</div>

                        <el-dropdown style="display:float;float:right" @command="handleCommand">
                            <el-avatar :src="useEmployeeInfo.info.eimg ? useEmployeeInfo.info.eimg : avatar">
                                <arrow-down />
                            </el-avatar>

                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item command="修改信息">修改信息</el-dropdown-item>
                                    <el-dropdown-item command="修改密码">修改密码</el-dropdown-item>
                                    <el-dropdown-item command="退出登录">退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </el-header>
                    <el-divider style="margin-bottom: 0;margin-top: 0.5;" />
                    <el-main>
                        <router-view></router-view>
                    </el-main>

                    <el-footer style="text-align: center">舌尖导航 ©2024 Created by Elliot</el-footer>
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>

<style scoped></style>