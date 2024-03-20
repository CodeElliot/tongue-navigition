<template>
  <el-container style="height: 100%;">
    <el-main>
      <div style="margin-bottom: 5px;">
        <el-button @click="getChartData('three')" autofocus>近三天</el-button>
        <el-button @click="getChartData('seven')">近七天</el-button>
        <el-button @click="getChartData('week')">本周</el-button>
      </div>
      <el-card style="height: 90%;">
        <div style="display: flex;flex-direction: row;justify-content: space-around;">
          <!-- 新增菜品数量统计 -->
          <div id="dishChart" style="width: 500px;height: 330px;"></div>
          <!-- 新增用户数量统计 -->
          <div id="userChart" style="width: 500px;height: 330px;"></div>
        </div>
        <div style="display: flex;flex-direction: row;justify-content: space-around;">
          <!-- 按口味统计菜品 -->
          <div id="flavorChart" style="width: 500px;height: 330px;"></div>
          <!-- 按分类统计菜品 -->
          <div id="categoryChart" style="width: 500px;height: 330px;"></div>
        </div>

      </el-card>
    </el-main>
  </el-container>
</template>

<script setup>
import * as  echarts from 'echarts';
import { ref, onMounted } from 'vue'
import { dishChartListService, flavorChartListService, userChartListService, categoryChartListService } from "@/api/chartApi.js"
var myDishChart;
var myUserChart;
var myFlavorChart;
var myCategoryChart;
const dishOption = ref({
  title: {
    text: '新增菜品统计'
  },
  tooltip: {},
  xAxis: {
    data: [0, 0, 0]
  },
  yAxis: {},
  series: [
    {
      name: '新增菜品数',
      type: 'line',
      data: [0, 0, 0]
    }
  ]
})
const userOption = ref({
  title: {
    text: '新增用户统计'
  },
  tooltip: {},
  xAxis: {
    data: [0, 0, 0]
  },
  yAxis: {},
  series: [
    {
      name: '新增用户数',
      type: 'line',
      data: [0, 0, 0]
    }
  ]
})
const flavorOption = ref({
  title: {
    text: '口味统计'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: '口味包含的菜谱数',
      type: 'pie',
      radius: '50%',
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
})
const categoryOption = ref({
  title: {
    text: '分类统计'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: '分类包含菜谱数',
      type: 'pie',
      radius: '50%',
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
})
onMounted(() => {
  myDishChart = echarts.init(document.getElementById('dishChart'));
  myUserChart = echarts.init(document.getElementById('userChart'));
  myFlavorChart = echarts.init(document.getElementById('flavorChart'));
  myCategoryChart = echarts.init(document.getElementById('categoryChart'));
  //菜品
  myDishChart.setOption(dishOption.value);
  //用户
  myUserChart.setOption(userOption.value);
  //口味
  myFlavorChart.setOption(flavorOption.value);
  //分类
  myCategoryChart.setOption(categoryOption.value);
})

const getChartData = async (flag) => {
  if (flag == 'three') {
    const begin = new Date()
    const end = new Date()
    begin.setDate(begin.getDate() - 2)
    const param = {
      begin: begin.toLocaleDateString(),
      end: end.toLocaleDateString()
    }
    getStatisticsData(param)


  } else if (flag == 'seven') {
    const begin = new Date()
    const end = new Date()
    begin.setDate(begin.getDate() - 6)
    const param = {
      begin: begin.toLocaleDateString(),
      end: end.toLocaleDateString()
    }
    getStatisticsData(param)
  } else if (flag == "week") {
    const begin = new Date()
    const end = new Date()
    //获取周一与周日时间
    let week=begin.getDay()
    if(week==0){
      //是星期日
      week=7
    }
    begin.setDate(begin.getDate() -week+1 )
    end.setDate(begin.getDate()+6)
    const param = {
      begin: begin.toLocaleDateString(),
      end: end.toLocaleDateString()
    }
    getStatisticsData(param)
  }
}

//通用访问
const getStatisticsData=async(param)=>{
  let dishRe = await dishChartListService(param)
    let userRe = await userChartListService(param)
    let flavorRe = await flavorChartListService()
    let categoryRe = await categoryChartListService()
    //修改菜谱数据
    dishOption.value.xAxis.data = dishRe.data.dateList
    dishOption.value.series[0].data = dishRe.data.newDataList
    myDishChart.setOption(dishOption.value)
    //修改用户数据
    userOption.value.xAxis.data = userRe.data.dateList
    userOption.value.series[0].data = userRe.data.newDataList
    myUserChart.setOption(userOption.value)
    //修改口味数据
    flavorOption.value.series[0].data = flavorRe.data.data
    myFlavorChart.setOption(flavorOption.value)
    //修改分类数据
    categoryOption.value.series[0].data = categoryRe.data.data
    console.log(categoryOption.value)
    myCategoryChart.setOption(categoryOption.value)
}
getChartData("three")
</script>

<style scoped></style>