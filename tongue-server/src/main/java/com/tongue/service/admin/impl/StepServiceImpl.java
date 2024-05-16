package com.tongue.service.admin.impl;

import com.tongue.constant.MessageConstant;
import com.tongue.dto.StepDTO;
import com.tongue.entity.Dish;
import com.tongue.entity.Step;
import com.tongue.exception.DishNotFoundException;
import com.tongue.exception.StepTooLongException;
import com.tongue.mapper.admin.DishMapper;
import com.tongue.mapper.admin.StepMapper;
import com.tongue.service.admin.StepService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepServiceImpl implements StepService {
    @Autowired
    private StepMapper stepMapper;
    @Autowired
    private DishMapper dishMapper;
    /**
     * 为指定菜品添加步骤
     * @param step
     */
    public void addStep(Step step) {
        Long dishId=step.getDishId();
        if (dishId==null||"".equals(dishId)){
            throw new DishNotFoundException(MessageConstant.DISH_NOT_FOUND);
        }
        Dish dish=dishMapper.getDishById(dishId);
        if(dish==null){
            throw new DishNotFoundException(MessageConstant.DISH_NOT_FOUND);
        }
        //步骤内容过长
        if (step.getContent().length()>500){
            throw new StepTooLongException(MessageConstant.STEP_TOO_LONG);
        }
        //添加步骤，并绑定到菜品上
        stepMapper.addStep(step);
    }

    /**
     * 根据菜品id删除步骤
     * @param id
     */
    public void deleteByDishId(Long id) {
        stepMapper.deleteByDishId(id);
    }

    /**
     * 更新步骤信息
     * @param step
     */
    public void update(Step step) {
        stepMapper.update(step);
    }
}
