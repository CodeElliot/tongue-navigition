import instance from "@/utils/request"
//查寻新增菜品数量
export const dishChartListService=(param)=>{
    return instance.get('/admin/chart/dishStatistic',{params:param})
}
//查寻新增用户数量
export const userChartListService=(param)=>{
    return instance.get('/admin/chart/userStatistic',{params:param})
}
//查各口味包含菜谱数量
export const flavorChartListService=(param)=>{
    return instance.get('/admin/chart/flavorStatistic',{params:param})
}
//查各分类包含菜谱数量
export const categoryChartListService=(param)=>{
    return instance.get('/admin/chart/categoryStatistic',{params:param})
}