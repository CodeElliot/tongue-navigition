//持节化token
import { defineStore } from "pinia";
import { ref } from "vue";
export const useTokenStore=defineStore('token',
()=>{
    //定义描述token
    const token=ref('')
    //定义修改token的方法
    const setToken=(newToken)=>{
        token.value=newToken
    }
    //定义移除token的方法
    const removeToken=()=>{
        token.value=''
    }
    return{
        token,setToken,removeToken
    }
},{
    persist:true//开启持久化存储
})