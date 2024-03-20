<script setup>
import { ElMessage, ElMessageBox } from "element-plus"
import { Plus } from "@element-plus/icons-vue"
import { employeeListService, employeeRegisterService } from '@/api/employeeApi'
import { ref } from 'vue'
import { useEmployeeInfoStore } from "@/stores/employeeInfo.js"
import {useTokenStore} from "@/stores/token.js"
const useEmployeeInfo = useEmployeeInfoStore()
const useToken=useTokenStore()
const employeeSearchForm = ref({
    pageNumber: 1,
    pageSize: 5,
    ename: '',
    account: ''
})
const tableData = ref([])
const total = ref(0)
const adminHere = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref()
const employeeForm = ref({
    ename: '',
    account: '',
    password: '',
    eimg: ''
})
//方法
//条件搜索员工
const searchByKey = async () => {
    const re = await employeeListService(employeeSearchForm.value)
    tableData.value = re.data.items
    total.value = re.data.total
}
//判断当前员工是否为管理员，是则获取数据
const isAdmin = () => {
    if (useEmployeeInfo.info.account == "admin" && useEmployeeInfo.info.id == "1") {
        searchByKey()
        adminHere.value = true
    }
}
//检查表格是否为admin，如果为admin则不能对其操作
const checkAdminOperation = (row) => {
    if (row.id == 1) {
        return false
    } else {
        return true
    }
}
//添加与修改员工
const addOrUpdateDish = (data, title) => {
    if (title == "添加员工") {
        dialogTitle.value = title
        employeeForm.value = {}
        dialogVisible.value = true
    } else if (title == "修改员工") {
        dialogTitle.value = title
        employeeForm.value.ename = data.ename
        employeeForm.value.eimg = data.eimg
        employeeForm.value.password = data.password
        dialogVisible.value = true
    }

}
//提交数据
const submitForm = async () => {
    let result = await employeeRegisterService(employeeForm.value)
    ElMessage.success(result.msg ? result.msg : "注册新员工成功")
    dialogVisible.value = false
    isAdmin()
}
//重置密码
const resetPassword=(id)=>{
    const dataForm={
        
    }
}
isAdmin()
</script>

<template>
    <div>
        <el-container>
            <el-header>
                <div class="search" v-if="adminHere">
                    <span class="search-key">员工名：</span>
                    <el-input v-model="employeeSearchForm.ename" style="width:150px" placeholder="请输入员工名" clearable />
                    <span class="search-key">账号：</span>
                    <el-input v-model="employeeSearchForm.account" style="width:150px" placeholder="请输入账号" clearable />
                    <el-button type="primary" style="margin-left: 10px;" @click="searchByKey">搜索</el-button>
                    <el-button type="primary" style="margin-left: 10px;"
                        @click="addOrUpdateDish(null, '添加员工')">添加员工</el-button>
                </div>
            </el-header>
        </el-container>

        <!-- 主要数据表格区域 -->
        <el-card v-if="adminHere">
            <el-empty v-if="total == 0" description="暂未找到任何数据" />
            <el-table v-else stripe :data="tableData" height="550" style="width: 100%;text-align:center"
                empty-text="暂无数据">
                <el-table-column type="selection" width="55" :selectable="checkAdminOperation" />
                <el-table-column fixed="left" width="50" prop="id" label="id" />
                <el-table-column prop="ename" label="员工名" width="150" />
                <el-table-column label="头像" width="150">
                    <template #default="scope">
                        <el-image :src="scope.row.eimg"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="account" label="账号" />
                <el-table-column fixed="right" label="操作" width="200">

                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="addOrUpdateDish(scope.row, '修改员工')"
                            :disabled="scope.row.id == 1">修改</el-button>
                        <el-button link type="danger" size="small" @click="deleteDish(scope.row.id)"
                            :disabled="scope.row.id == 1">删除</el-button>
                            <el-button link size="small" type="danger" @click="resetPassword(scope.row.id)">重置密码</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <!-- 添加与修改 -->
        <el-dialog v-model="dialogVisible" :title="dialogTitle" width="300px">
            <el-form>
                <el-form-item label="员工名">
                    <el-input placeholder="请输入员工名" v-model="employeeForm.ename"></el-input>
                </el-form-item>
                <el-form-item label="账&nbsp&nbsp&nbsp&nbsp号" v-if="dialogTitle == '添加员工'">
                    <el-input placeholder="请输入账号" v-model="employeeForm.account"></el-input>
                </el-form-item>
                <el-form-item label="头&nbsp&nbsp&nbsp&nbsp像" v-if="dialogTitle == '修改员工'">
                    <el-upload class="avatar-uploader" action="/api/upload" :headers="{ 'token': useToken.token }"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="employeeForm.eimg" :src="employeeForm.eimg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>

            </el-form>
            <el-footer style="text-align: center;">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitForm">提交</el-button>
            </el-footer>
        </el-dialog>
        <div class="pager" v-if="adminHere">
            <el-pagination v-model:current-page="employeeSearchForm.pageNumber"
                v-model:page-size="employeeSearchForm.pageSize" :page-sizes="[5, 10, 15, 20]" :small="small"
                :disabled="disabled" :background="background" layout="total, sizes, prev, pager, next, jumper"
                :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
        <el-card v-if="!adminHere">
            <el-empty description="当前账号无权操作本页面"></el-empty>
        </el-card>
    </div>
</template>

<style scoped>
.search-key {
    margin-left: 10px;
}

.pager {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    margin-top: 30px;
}
</style>