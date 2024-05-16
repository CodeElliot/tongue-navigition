package com.tongue.controller.admin;

import com.tongue.constant.MessageConstant;
import com.tongue.dto.DishDTO;
import com.tongue.dto.StepDTO;
import com.tongue.entity.Category;
import com.tongue.entity.Dish;
import com.tongue.entity.Flavor;
import com.tongue.entity.Step;
import com.tongue.result.PageResult;
import com.tongue.result.Result;
import com.tongue.service.admin.DishService;
import com.tongue.service.admin.StepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Tag(name = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private StepService stepService;

    /**
     * 添加菜品:需要返回一个dishId用于之后的添加步骤操作
     * @param dishDTO
     * @return
     */
    @Operation(summary = "菜品添加")
    @PostMapping("/add")
    public Result add(@RequestBody DishDTO dishDTO){
        Long dishId=dishService.addDish(dishDTO);
        log.info("添加成功菜品的id：{}",dishId);
        return Result.success(MessageConstant.DISH_ADD_SUCCESS,dishId);
    }
    /**
     * 查询菜品
     */
    @Operation(summary = "菜品查询")
    @GetMapping("/list")
    public Result<PageResult<Dish>> list(Integer pageNumber,Integer pageSize,
                                   @RequestParam(required = false) String dname,
                                   @RequestParam(required = false) Integer categoryId,
                                   @RequestParam(required = false) Integer flavorId,
                                   @RequestParam(required = false) String ingredient
                                   ){
        PageResult<Dish> pageResult=dishService.list(pageNumber,pageSize,dname,categoryId,flavorId,ingredient,null);
        return Result.success(MessageConstant.COMMON_RESULT,pageResult);
    }
    /**
     * 修改菜品
     */
    @Operation(summary = "菜品修改")
    @PatchMapping("/update")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success(MessageConstant.COMMON_RESULT);
    }
    /**
     * 删除菜品
     */
    @Operation(summary = "菜品删除")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        dishService.delete(id);
        return Result.success(MessageConstant.COMMON_RESULT);
    }
    /**
     * 查询口味
     */
    @Operation(summary = "菜品口味查询")
    @GetMapping("/flavor")
    public Result<List<Flavor>> getFlavors(){
       List<Flavor> flavors= dishService.getFlavors();
       return Result.success(MessageConstant.COMMON_RESULT,flavors);
    }
    /**
     * 查询分类
     */
    @Operation(summary = "菜品分类查询")
    @GetMapping("/category")
    public Result<List<Category>> getCategories(){
        List<Category> categories= dishService.getCategories();
        return Result.success(MessageConstant.COMMON_RESULT,categories);
    }
}
