//定制请求实例
import axios from "axios"
import { ElMessage } from "element-plus"
import { useTokenStore } from "@/stores/token"
//定义公共前缀
const baseURL='/api'
const instance=axios.create({baseURL})

//导入路由
import router from "@/router/index.js"
//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //统一管理响应结果
        if(result.data.code==1){
            return result.data
        }
        //失败了
        ElMessage.error(result.data.msg)
        return Promise.reject(result.data)
    },
    err=>{
        if(err.response.status==401){
            ElMessage.error("请登录先")
            router.push('/login')
        }else{
            ElMessage.error("服务异常")
        }
        return Promise.reject(err)//异步的状态转化为失败的状态
    }
)
//添加请求拦截器
instance.interceptors.request.use(
    (config)=>{
        //请求前的回调
        //添加token
        const useToken=useTokenStore()
        //判断是否有token
        if(useToken.token){
            config.headers.token=useToken.token
        }
        return config
    },
    (err)=>{
        //请求错误的回调
        Promise.reject(err)
    }
)
export default instance