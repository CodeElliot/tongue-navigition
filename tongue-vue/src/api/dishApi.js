import instance from "@/utils/request";
//条件分页查询
export const dishListService=(param)=>{
    //借助UrlSearchParams完成传递（因为接口文档中需要传递的参数类型是x-www-form-urlencode)
    // const params=new URLSearchParams()
    // for(let key in dishSearchParams){
    //     params.append(key,dishSearchParams[key])
    // }
    return instance.get('/admin/dish/list',{params:param})
}
//查询口味
export const flavorListService=()=>{
    return instance.get("/admin/dish/flavor")
}
//查询分类
export const categoryListService=()=>{
    return instance.get("/admin/dish/category")
}
//删除菜品
export const dishDeleteService=(id)=>{
    return instance.delete('/admin/dish/delete/'+id)
}
//添加菜品
export const dishAddService=(dishForm)=>{
    return instance.post('/admin/dish/add',dishForm)
}
//修改菜品
export const dishUpdateService=(dishForm)=>{
    return instance.patch('/admin/dish/update',dishForm)
}