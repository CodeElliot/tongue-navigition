package com.tongue.service.admin.impl;

import com.tongue.entity.Category;
import com.tongue.entity.Flavor;
import com.tongue.mapper.admin.DishMapper;
import com.tongue.mapper.user.UserMapper;
import com.tongue.service.admin.ChartService;
import com.tongue.vo.ChartByFlavorAndCategoryVO;
import com.tongue.vo.ChartVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增菜品数据统计
     *
     * @param begin
     * @param end
     * @return
     */
    public ChartVO getDishStatistic(LocalDate begin, LocalDate end) {
        //存放从begin到end之间的时间
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //存放每天新增菜品数量
        List<Integer> newDishDateList = new ArrayList<>();
        //遍历时间，查询数据
        for (LocalDate date : dateList) {
            //声明最大最小时间点
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            newDishDateList.add(dishMapper.getNewDishStatistic(map));
        }
        return ChartVO.builder()
                .dateList(dateList)
                .newDataList(newDishDateList)
                .build();
    }

    /**
     * 新增用户数据统计
     *
     * @param begin
     * @param end
     * @return
     */
    public ChartVO getUserStatistic(LocalDate begin, LocalDate end) {
        //存放从begin到end之间的时间
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //存放每天新增用户数量
        List<Integer> newUserDateList = new ArrayList<>();
        //遍历时间，查询数据
        for (LocalDate date : dateList) {
            //声明最大最小时间点
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            newUserDateList.add(dishMapper.getNewUserStatistic(map));
        }
        return ChartVO.builder()
                .dateList(dateList)
                .newDataList(newUserDateList)
                .build();
    }

    /**
     * 获取口味总量数据
     *
     * @return
     */
    public ChartByFlavorAndCategoryVO getFlavorStatistic() {
        //存放每个口味菜品总量数量
        List<Map<String, Object>> maps = new ArrayList<>();
        //获取所有口味
        List<Flavor> flavors = dishMapper.getFlavors();
        for (Flavor flavor : flavors) {
            Long count = dishMapper.countByFlavor(flavor.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("name", flavor.getFname());
            map.put("value", count);
            maps.add(map);
        }

        return ChartByFlavorAndCategoryVO.builder()
                .data(maps)
                .build();
    }

    /**
     * 获取分类总量数据
     *
     * @return
     */
    public ChartByFlavorAndCategoryVO getCategoryStatistic() {
        //存放每个分类菜品总量数量
        List<Map<String, Object>> maps = new ArrayList<>();
        //获取所有分类
        List<Category> categories = dishMapper.getCategories();
        for (Category category  : categories) {
            Long count = dishMapper.countByCategory(category.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("name", category.getCname());
            map.put("value", count);
            maps.add(map);
        }

        return ChartByFlavorAndCategoryVO.builder()
                .data(maps)
                .build();
    }
}
