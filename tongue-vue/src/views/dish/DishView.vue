<template>
  <div style="text-align:center">
    <el-container>
      <el-header>
        <div class="search">
          <span class="search-key">菜名：</span>
          <el-input v-model="dishSearchParams.dname" style="width:150px" placeholder="请输入菜名" clearable />
          <span class="search-key">口味：</span>
          <el-select style="width:150px" placeholder="请选择口味" v-model="dishSearchParams.flavorId" clearable>
            <el-option v-for="item in flavors" :key="item.fname" :label="item.fname" :value="item.id" />
          </el-select>
          <span class="search-key">食材：</span>
          <el-input v-model="dishSearchParams.ingredient" style="width:150px" placeholder="请输入食材" clearable />
          <span class="search-key">分类：</span>
          <el-select style="width:150px" placeholder="请选择分类" v-model="dishSearchParams.categoryId" clearable>
            <el-option v-for="item in categoris" :key="item.cname" :label="item.cname" :value="item.id" />
          </el-select>
          <el-button style="margin-left: 10px;" type="primary" @click="searchForKey" :icon="Search" />
          <el-button type="danger" @click="clearAllArgs" :icon="Delete" />
          <el-button type="primary" style="margin-left: 25%;" @click="addOrUpdateDish(null, 'add')">添加菜品</el-button>
        </div>

      </el-header>
    </el-container>
    <!-- 添加菜品对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500" :before-close="handleClose">
      <el-form v-if="!stepOver" :model="dishForm" label-width="auto" style="max-width: 600px">
        <el-form-item label="菜品名">
          <el-input v-model="dishForm.dname" placeholder="请输入菜品名"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload class="avatar-uploader" action="/api/upload" :headers="{ 'token': useToken.token }"
            :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="dishForm.dimg" :src="dishForm.dimg" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="dishForm.description" placeholder="请输入一个简介"></el-input>
        </el-form-item>
        <el-form-item label="主要食材">
          <div style="display: flex;flex-direction: row;">
            <el-input v-model="ingredientForm[0]" placeholder="食材1"></el-input>
            <el-input v-model="ingredientForm[1]" :disabled="ingredientForm[0] == null" placeholder="食材2"></el-input>
            <el-input v-model="ingredientForm[2]" :disabled="ingredientForm[1] == null" placeholder="食材3"></el-input>
            <el-input v-model="ingredientForm[3]" :disabled="ingredientForm[2] == null" placeholder="食材4"></el-input>
            <el-input v-model="ingredientForm[4]" :disabled="ingredientForm[3] == null" placeholder="食材5"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="分类">
          <el-select style="width:150px" placeholder="请选择分类" v-model="dishForm.categoryId" clearable>
            <el-option v-for="item in categoris" :key="item.cname" :label="item.cname" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="口味">
          <el-select style="width:150px" placeholder="请选择口味" v-model="dishForm.flavorId" clearable>
            <el-option v-for="item in flavors" :key="item.fname" :label="item.fname" :value="item.id" />
          </el-select>
        </el-form-item>

      </el-form>
      <el-form v-else>
        <el-form-item label="步骤一">
          <el-input v-model="stepForm[0]" placeholder="添加第一个步骤"></el-input>
        </el-form-item>
        <el-form-item label="步骤二">
          <el-input v-model="stepForm[1]" :disabled="stepForm[0] == null" placeholder="添加第二个步骤"></el-input>
        </el-form-item>
        <el-form-item label="步骤三">
          <el-input v-model="stepForm[2]" :disabled="stepForm[1] == null" placeholder="添加第三个步骤"></el-input>
        </el-form-item>
        <el-form-item label="步骤四">
          <el-input v-model="stepForm[3]" :disabled="stepForm[2] == null" placeholder="添加第四个步骤"></el-input>
        </el-form-item>
        <el-form-item label="步骤五">
          <el-input v-model="stepForm[4]" :disabled="stepForm[3] == null" placeholder="添加第五个步骤"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addSteps('提交')">
            {{ buttonTitle }}
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 步骤详情 -->
    <el-dialog title="步骤详情" v-model="showStepDetail">
      <el-empty v-if="stepDetail.length == 0"></el-empty>
      <el-form v-else :model="stepDetail" label-width="auto">
        <el-form-item label="步骤一" v-if="stepDetail[0]">
          <el-input v-model="stepDetail[0]" disabled autosize></el-input>
        </el-form-item>
        <el-form-item label="步骤二" v-if="stepDetail[1]">
          <el-input v-model="stepDetail[1]" disabled></el-input>
        </el-form-item>
        <el-form-item label="步骤三" v-if="stepDetail[2]">
          <el-input v-model="stepDetail[2]" disabled></el-input>
        </el-form-item>
        <el-form-item label="步骤四" v-if="stepDetail[3]">
          <el-input v-model="stepDetail[3]" disabled></el-input>
        </el-form-item>
        <el-form-item label="步骤五" v-if="stepDetail[4]">
          <el-input v-model="stepDetail[4]" disabled></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 主要数据表格区域 -->
    <el-card>
      <el-empty v-if="total == 0" description="暂未找到任何数据" />
      <el-table v-else stripe :data="tableData" height="550" style="width: 100%;text-align:center" empty-text="暂无数据">
        <el-table-column type="selection" width="55" />
        <el-table-column fixed="left" prop="id" width="50" label="id" />
        <el-table-column prop="dname" label="菜名" width="150" />
        <el-table-column label="图片">

          <template #default="scope">
            <el-image :src="scope.row.dimg"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="cname" label="分类" />
        <el-table-column prop="fname" label="口味" />
        <el-table-column prop="ingredient" label="食材" />
        <el-table-column label="步骤">

          <template #default="scope">
            <el-button link type="primary" @click="detailStep(scope.row.step)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="click" label="点击率" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column prop="createName" label="创建人" />
        <el-table-column fixed="right" label="操作" width="150">

          <template #default="scope">
            <el-button link type="primary" size="small" @click="addOrUpdateDish(scope.row, 'update')">修改</el-button>
            <el-button link type="danger" size="small" @click="deleteDish(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <div class="pager">
      <el-pagination v-model:current-page="dishSearchParams.pageNumber" v-model:page-size="dishSearchParams.pageSize"
        :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<script setup>
import { Search, Delete, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
//导入请求api
import { dishListService, flavorListService, categoryListService, dishDeleteService, dishAddService,dishUpdateService } from "@/api/dishApi.js"
import { useTokenStore } from "@/stores/token.js"
import { ref } from "vue"
const tableData = ref([])
const total = ref(0)
const dishSearchParams = ref({ pageNumber: 1, pageSize: 5, dname: '', flavorId: '', ingredient: '', categoryId: '' })
//定义口味数据表和分类数据表
const flavors = ref([])
const categoris = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref("添加菜品")
const useToken = useTokenStore()
const showStepDetail = ref(false)
const dishForm = ref({
  dname: '',
  dimg: '',
  categoryId: '',
  flavorId: '',
  step: '',
  ingredient: '',
  description: ''
})
//步骤详情
const stepDetail = ref([])
//食材
const ingredientForm = ref([])
//存储步骤添加或修改的信息
const stepForm = ref([])
const buttonTitle = ref("添加步骤")
const stepOver = ref(false)
// 按条件查找
const searchForKey = async () => {
  let param = {
    pageNumber: dishSearchParams.value.pageNumber,
    pageSize: dishSearchParams.value.pageSize,
    dname: dishSearchParams.value.dname ? dishSearchParams.value.dname : null,
    flavorId: dishSearchParams.value.flavorId ? dishSearchParams.value.flavorId : null,
    ingredient: dishSearchParams.value.ingredient,
    categoryId: dishSearchParams.value.categoryId ? dishSearchParams.value.categoryId : null,
  }
  let result = await dishListService(param)
  tableData.value = result.data.items
  total.value = result.data.total
}
//查询口味与分类数据
const searchFlavorsAndCatogories = async () => {
  let result1 = await flavorListService()
  let result2 = await categoryListService()
  flavors.value = result1.data
  categoris.value = result2.data
}
//改变页面数据展示条数
const handleSizeChange = (value) => {
  dishSearchParams.value.pageSize = value
  searchForKey()
}
//改变当前页
const handleCurrentChange = (value) => {
  dishSearchParams.value.pageNumber = value
  searchForKey()
}
//清空查询条件
const clearAllArgs = () => {
  dishSearchParams.value.pageNumber = 1
  dishSearchParams.value.pageSize = 5
  dishSearchParams.value.dname = ''
  dishSearchParams.value.flavorId = ''
  dishSearchParams.value.ingredient = ''
  dishSearchParams.value.categoryId = ''

}
//清空添加的信息
const clearDishForm = () => {
  dishForm.value.dname = ''
  dishForm.value.dimg = ''
  dishForm.value.categoryId = ''
  dishForm.value.flavorId = ''
  dishForm.value.step = ''
  dishForm.value.ingredient = ''
  dishForm.value.description = ''
  ingredientForm.value = []
  stepForm.value = []
}

//修改/添加菜品
const addOrUpdateDish = (dishData, title) => {
  clearDishForm()
  stepOver.value = false
  if (title == "update") {
    buttonTitle.value = "修改步骤"
    dialogTitle.value = "修改菜品"
    //数据回显
    dishForm.value.dname = dishData.dname
    dishForm.value.description = dishData.description
    dishForm.value.categoryId = dishData.categoryId
    dishForm.value.flavorId = dishData.flavorId
    dishForm.value.step = dishData.step
    dishForm.value.id=dishData.id
    stepForm.value = dishData.step.split('_')
    ingredientForm.value = dishData.ingredient.split('_')
  } else if (title == "add") {
    buttonTitle.value = "添加步骤"
    dialogTitle.value = "添加菜品"
  }
  dialogVisible.value = true

}
//删除菜品
const deleteDish = (id) => {
  ElMessageBox.confirm(
    '确定删除吗？',
    'Warning',
    {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      let re = await dishDeleteService(id)
      ElMessage({
        type: 'success',
        message: re.msg,
      })
      searchForKey()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
  console.log(id)
}
//拼接步骤与食材
const montageStepAndIngredient = () => {
  let stepResult = ref('')
  let ingreResult = ref('')
  //拼接步骤
  for (let key in stepForm.value) {
    if (stepForm.value[key] != '') {
      stepResult.value = stepResult.value + stepForm.value[key] + '_'
    }
  }
  //拼接食材
  for (let key in ingredientForm.value) {
    if (ingredientForm.value[key] != '') {
      ingreResult.value = ingreResult.value + ingredientForm.value[key] + '_'
    }
  }
  stepResult.value = stepResult.value.slice(0, stepResult.value.length - 1)
  ingreResult.value = ingreResult.value.slice(0, ingreResult.value.length - 1)
  dishForm.value.step = stepResult.value
  dishForm.value.ingredient = ingreResult.value
  console.log(dishForm.value)
}
//添加/修改步骤to提交
const addSteps =async(title) => {
  if (title == "提交" && stepOver.value == true) {
    //填写完成，准备提交
    dialogVisible.value = false
    //拼接数据
    montageStepAndIngredient()
    if(dialogTitle.value=="添加菜品"){
      console.log("添加")
      let result =await dishAddService(dishForm.value)
      ElMessage.success(result.msg)
    }else if(dialogTitle.value=="修改菜品"){
      console.log("修改菜品")
      console.log(dishForm.value)
      let result=await dishUpdateService(dishForm.value)
      ElMessage.success(result.msg)
    }
    //刷新数据
    searchForKey() 
  }
  stepOver.value = true
  buttonTitle.value = title
}
//查看步骤详情
const detailStep = (stepData) => {
  const re = stepData.split('_')
  console.log(re)
  stepDetail.value = re
  showStepDetail.value = true
}
//图片上传成功方法
const handleAvatarSuccess = (responce, uploadFile) => {
  console.log(responce.data)
  dishForm.value.dimg = responce.data
}
//图片检查
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片必须是jpg格式！')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能大于2MB')
    return false
  }
  return true
}
//页面加载完成后便开始查找
searchForKey()
searchFlavorsAndCatogories()
</script>

<style scoped>
.pager {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  margin-top: 30px;
}

.search {
  display: flex;
  flex-direction: row;
  margin-bottom: 30px;

}

.el-input {
  margin-right: 10px;
}

.search-key {
  margin-top: 5px;
}

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