import instance from "@/utils/request";
import request from "@/utils/request";
//员工登录
export const employeeLoginService=(emploee)=>{
    return instance.post('/admin/employee/login',emploee)
}
//员工查询
export const employeeListService=(param)=>{
    return instance.get('/admin/employee/list',{params:param})
}
//员工更新个人信息
export const employeeUpdateInfoService=(employee)=>{
    return instance.put('/admin/employee/update',employee)
} 
//员工修改密码
export const employeeUpdatePasswordService=(password)=>{
    return instance.put('/admin/employee/repassword',password)
} 
//注册
export const employeeRegisterService=(form)=>{
    return instance.post('/admin/employee/register',form)
} 