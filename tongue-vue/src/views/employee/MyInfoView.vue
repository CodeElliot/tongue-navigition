<template>
    <div>
        <el-container>
            <div style="width:100%">
                <div style="display: flex;flex-direction: row;justify-content: space-around;text-align: center;">
                    <el-card style="width: 400px;">
                        <el-form>
                            <el-form-item label="账&nbsp&nbsp&nbsp&nbsp号">
                                <el-input v-model="myInfoForm.account" disabled></el-input>
                            </el-form-item>

                            <el-form-item label="用户名">
                                <el-input v-model="myInfoForm.ename" disabled></el-input>
                            </el-form-item>
                            <el-form-item label="头&nbsp&nbsp&nbsp&nbsp像">
                                <el-image v-if="myInfoForm.eimg" v-alt="myInfoForm.eimg"></el-image>
                                <el-input v-else disabled model-value="无"></el-input>
                            </el-form-item>
                            <el-footer>
                                <el-button type="primary" @click="showUpdateInfo = true">修改信息</el-button>
                                <el-button type="primary" @click="showUpdatePassword = true">修改密码</el-button>
                            </el-footer>
                        </el-form>
                    </el-card>
                    <!-- 修改信息对话框 -->
                    <el-dialog v-model="showUpdateInfo" width="500">
                        <el-form>
                            <el-form-item label="新名字">
                                <el-input v-model="updateInfoForm.ename" placeholder="请输入新名字"></el-input>
                            </el-form-item>
                            <el-form-item label="新头像">
                                <el-upload class="avatar-uploader" action="/api/upload"
                                    :headers="{ 'token': useToken.token }" :show-file-list="false"
                                    :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                                    <el-icon class="avatar-uploader-icon">
                                        <Plus />
                                    </el-icon>
                                </el-upload>
                            </el-form-item>
                        </el-form>
                        <el-footer>
                            <el-button>取消</el-button>
                            <el-button type="primary" @click="submitUpdateForm('info')">提交</el-button>
                        </el-footer>
                    </el-dialog>
                    <el-dialog v-model="showUpdatePassword" style="width: 300px;">
                        <el-form>
                            <el-form-item label="旧&nbsp&nbsp密&nbsp码">
                                <el-input type="password" v-model="updatePasswordForm.password" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="新&nbsp&nbsp密&nbsp码">
                                <el-input type="password" v-model="updatePasswordForm.newpassword" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码">
                                <el-input type="password" v-model="updatePasswordForm.repassword" show-password></el-input>
                            </el-form-item>
                        </el-form>
                        <el-footer>
                            <el-button>取消</el-button>
                            <el-button type="primary" @click="submitUpdateForm('password')">提交</el-button>
                        </el-footer>
                    </el-dialog>
                </div>
            </div>
        </el-container>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useEmployeeInfoStore } from '@/stores/employeeInfo'
import { useTokenStore } from '@/stores/token.js'
import {employeeUpdateInfoService,employeeUpdatePasswordService} from "@/api/employeeApi.js"
import router from '@/router'
const useEmployeeInfo = useEmployeeInfoStore()
const useToken = useTokenStore()
//解构
const myInfoForm = ref({...useEmployeeInfo.info})
const showUpdateInfo = ref(false)
const showUpdatePassword = ref(false)
const updatePasswordForm = ref({
    account: '',
    password: '',
    newpassword: '',
    repassword: '',
})
const updateInfoForm = ref({
    eimg: '',
    ename: ''
}
)

//提交更新信息
const submitUpdateForm=async(flag)=>{
    if(flag=='password'){
        updatePasswordForm.value.account=myInfoForm.value.account
        await employeeUpdatePasswordService(updatePasswordForm.value)
        //清空旧信息
        useToken.removeToken()
        ElMessage.success("更新密码成功，请重新登录！")
        showUpdatePassword.value=false
        router.push('/login')
    }else if(flag=='info'){
        const result=await employeeUpdateInfoService(updateInfoForm.value)
        myInfoForm.value.eimg=updateInfoForm.value.eimg
        myInfoForm.value.ename=updateInfoForm.value.ename
        useEmployeeInfo.setInfo(myInfoForm.value)
        ElMessage.success(result.msg)
        showUpdateInfo.value=false
    }
}
</script>

<style scope>
.avatar-uploader .avatar {

    width: 178px;
    height: 178px;
    display: block;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}
</style>