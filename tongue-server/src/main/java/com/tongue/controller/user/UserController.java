package com.tongue.controller.user;

import com.tongue.constant.MessageConstant;
import com.tongue.dto.DishDTO;
import com.tongue.dto.UserLoginDTO;
import com.tongue.entity.Dish;
import com.tongue.result.PageResult;
import com.tongue.result.Result;
import com.tongue.service.admin.DishService;
import com.tongue.vo.DishVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//用户相关:登录、发布、收藏and取消收藏、删除自己的菜谱、查看、搜索、修改自己的菜谱
@RequestMapping("user/user")
@RestController
@Tag(name = "用户相关接口")
public class UserController {
    @Autowired
    private DishService dishService;
    /**
     * TODO:微信登录登录
     */

    public Result login(UserLoginDTO userLoginDTO){
        return null;
    }
    /**
     *查找菜谱
     */
    @GetMapping("/list")
    public Result<PageResult<Dish>> getDishList(Integer pageNumber,
                                                  Integer pageSize,
                                                  @RequestParam(required = false) String dname,
                                                  @RequestParam(required = false) Integer categoryId,
                                                  @RequestParam(required = false) Integer flavorId,
                                                  @RequestParam(required = false) String ingredient,
                                                  @RequestParam(required = false) String openid
    ){
        PageResult<Dish> pageResult = dishService.list(pageNumber, pageSize, dname, categoryId, flavorId, ingredient,openid);
        return Result.success(MessageConstant.COMMON_RESULT,pageResult);
    }
    /**
     *发布菜谱
     */
    public Result addMyDish(@RequestBody DishDTO dishDTO){
        dishService.addDishByUser(dishDTO);
        return Result.success(MessageConstant.DISH_ADD_SUCCESS);
    }
}
