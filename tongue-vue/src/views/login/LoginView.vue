<script setup>
import { reactive, ref } from "vue"
import { employeeLoginService } from '@/api/employeeApi.js'
import { ElMessage } from "element-plus";
import { useTokenStore } from "@/stores/token.js"
import { useEmployeeInfoStore } from "@/stores/employeeInfo.js"
import router from "@/router";
import loginImage from "@/assets/login.png"
const useToken = useTokenStore()
const useEmployeeInfo = useEmployeeInfoStore()
const employee = ref({
    account: "",
    password: ""
})

const rules = {
    account: [
        { required: true, message: '请输入账号', trigger: 'blur', },
        { min: 5, max: 20, message: '长度应为5~20', trigger: 'blur', },

    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur', },
        { min: 5, max: 20, message: '长度应为5~20', trigger: 'blur', },

    ],
}
const login = async (employee) => {
    const result = await employeeLoginService(employee)
    ElMessage.success(result.msg)
    //登陆成功，保存token,并将信息持久化
    useToken.setToken(result.data.token)
    console.log(result.data)
    useEmployeeInfo.setInfo(result.data)
    router.push('/')
}
</script>

<template>

    <div style="display: flex;flex-direction: row;height: 100%;background-color: #8c4a20;">
        <el-aside width="50%">
            <div>
                <el-image :src="loginImage" style="height: 100%;">

                </el-image>
            </div>
        </el-aside>
        <div style="height: 100%;display: flex;flex-direction: column;justify-content: space-around;">
            <div>
                <el-container>
                    <el-main>
                        <el-card style="margin-left: 300px">
                            <el-form :rules="rules" :model="employee" autocomplete="false" ref="employeeRef">
                                <el-form-item  label="账号" prop="account" autocomplete="off">
                                    <el-input placeholder="请输入账号" v-model="employee.account"></el-input>
                                </el-form-item>
                                <el-form-item label="密码" prop="password">
                                    <el-input type="password" placeholder="请输入密码"
                                        v-model="employee.password"></el-input>
                                </el-form-item>
                                <div style="text-align: center;">
                                    <el-button type="primary" size="large" @click="login(employee)">登录</el-button>
                                </div>
                            </el-form>
                        </el-card>
                    </el-main>
                </el-container>
            </div>
        </div>
    </div>

</template>

<style scoped></style>