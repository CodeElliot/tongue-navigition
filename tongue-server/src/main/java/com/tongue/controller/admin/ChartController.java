package com.tongue.controller.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tongue.constant.MessageConstant;
import com.tongue.dto.ChartDTO;
import com.tongue.result.Result;
import com.tongue.service.admin.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 图表
 */
@Slf4j
@RequestMapping("/admin/chart")
@RestController
public class ChartController {
    @Autowired
    private ChartService chartService;
    @GetMapping("/dishStatistic")
    public Result getDishStatistics( ChartDTO chartDTO){
        log.info("获取菜品图表数据");
        return Result.success(MessageConstant.COMMON_RESULT,chartService.getDishStatistic(chartDTO.getBegin(),chartDTO.getEnd()));
    }
    @GetMapping("/userStatistic")
    public Result getUserStatistics( ChartDTO chartDTO){
        log.info("获取用户图表数据");
        return Result.success(MessageConstant.COMMON_RESULT,chartService.getUserStatistic(chartDTO.getBegin(),chartDTO.getEnd()));
    }
    @GetMapping("/flavorStatistic")
    public Result getFlavorStatistics(){
        log.info("获取口味图表数据");
        return Result.success(MessageConstant.COMMON_RESULT,chartService.getFlavorStatistic());
    }
    @GetMapping("/categoryStatistic")
    public Result getCategoryStatistics(){
        log.info("获取分类图表数据");
        return Result.success(MessageConstant.COMMON_RESULT,chartService.getCategoryStatistic());
    }
}
