package com.tongue.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongue.constant.MessageConstant;
import com.tongue.dto.DishDTO;
import com.tongue.entity.Category;
import com.tongue.entity.Dish;
import com.tongue.entity.Flavor;
import com.tongue.entity.Step;
import com.tongue.exception.DishDataIsNullException;
import com.tongue.exception.DishNotFoundException;
import com.tongue.exception.StepTooLongException;
import com.tongue.mapper.admin.DishMapper;
import com.tongue.mapper.admin.StepMapper;
import com.tongue.result.PageResult;
import com.tongue.service.admin.DishService;
import com.tongue.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private StepMapper stepMapper;

    /**
     * 添加菜品
     *
     * @param dishDTO
     */
    @Transactional
    public Long addDish(DishDTO dishDTO) {
        String dname = dishDTO.getDname();
        Integer categoryId = dishDTO.getCategoryId();
        Integer flavorId = dishDTO.getFlavorId();
        String ingredient = dishDTO.getIngredient();//传过来的是用_分割的字符串。例如：鸡蛋_牛奶
        String step = dishDTO.getStep();
        //判断是否为空
        if (isNull(dname)) {
            throw new DishDataIsNullException(MessageConstant.DNAME_IS_NULL);
        }
        if (isNull(categoryId)) {
            throw new DishDataIsNullException(MessageConstant.CATEGORY_IS_NULL);
        }
        if (isNull(flavorId)) {
            throw new DishDataIsNullException(MessageConstant.FLAVOR_IS_NULL);
        }
        if (isNull(ingredient)) {
            throw new DishDataIsNullException(MessageConstant.INGREDIENT_IS_NULL);
        }
        if (step.length() > 500) {
            throw new StepTooLongException(MessageConstant.STEP_TOO_LONG);
        }
        Category category = dishMapper.getCategoryById(categoryId);
        Flavor flavor = dishMapper.getFlavorById(flavorId);
        //判断是否存在正确的口味与分类
        if (category == null || flavor == null) {
            throw new DishDataIsNullException(MessageConstant.FLAVOR_NOT_FOUND);
        }
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.addDish(dish);
        Long dishId = dish.getId();
        return dishId;
    }

    /**
     * 按条件分页查询菜品
     *
     * @param pageNumber
     * @param pageSize
     * @param dname
     * @param categoryId
     * @param flavorId
     * @param ingredient 字符串形的主要食材，通过_连接，需要转换为列表后，遍历查询
     * @return
     */
    public PageResult list(Integer pageNumber, Integer pageSize, String dname, Integer categoryId, Integer flavorId, String ingredient,String openid) {
        PageResult<DishVO> pageResult = new PageResult<>();
        PageHelper.startPage(pageNumber, pageSize);
        //将食材转换为列表
        List<String> ingredientList = List.of(ingredient.split("_"));

        Page<DishVO> page = dishMapper.list(dname, categoryId, flavorId, openid,ingredientList);
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(page.getResult());
        return pageResult;
    }

    /**
     * 更新菜品基本信息
     * TODO:数据验证不够严谨
     *
     * @param dishDTO
     */
    @Transactional
    public void update(DishDTO dishDTO) {
        Long id = dishDTO.getId();
        if (id == null || "".equals(id)) {
            throw new DishNotFoundException(MessageConstant.DISH_NOT_FOUND);
        }
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
    }

    /**
     * 删除菜品
     * TODO:修改为批量删除
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new DishNotFoundException(MessageConstant.DISH_NOT_FOUND);
        }
        dishMapper.delete(id);
    }

    /**
     * 查询口味
     * @return
     */
    public List<Flavor> getFlavors() {
        return dishMapper.getFlavors();
    }

    /**
     * 查询分类
     * @return
     */
    public List<Category> getCategories() {
        return dishMapper.getCategories();
    }

    /**
     * 用户添加菜品
     * @param dishDTO
     */
    public void addDishByUser(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.addDish(dish);
    }

    /**
     * 判断对象是否为空
     */
    private boolean isNull(Object obj) {
        if (obj == null || "".equals(obj)) {
            return true;
        }
        return false;
    }
}
