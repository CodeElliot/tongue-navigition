package com.tongue.mapper.admin;

import com.github.pagehelper.Page;
import com.tongue.annotation.AutoFill;
import com.tongue.dto.DishDTO;
import com.tongue.entity.Category;
import com.tongue.entity.Dish;
import com.tongue.entity.Flavor;
import com.tongue.enumeration.OperationType;
import com.tongue.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    //根据id查询分类
    @Select("select * from category where id=#{categoryId}")
    Category getCategoryById(Integer categoryId);
    //根据id查询口味
    @Select("select * from flavor where id=#{flavorId}")
    Flavor getFlavorById(Integer flavorId);

    //添加菜品
    @AutoFill(value=OperationType.INSERT)
    void addDish(Dish dish);
    //根据id查询菜品
    @Select("select * from dish where id=#{dishId}")
    Dish getDishById(Long dishId);
    //条件查询菜品
    Page<DishVO> list(String dname, Integer categoryId, Integer flavorId,String openid, List<String> ingredientList);
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
    @Delete("delete from dish where id=#{id}")
    void delete(Long id);
    @Select("select * from flavor")
    List<Flavor> getFlavors();
    @Select("select * from category")
    List<Category> getCategories();
    //查询菜品增量
    Integer getNewDishStatistic(Map map);

    Integer getNewUserStatistic(Map map);
    @Select("select count(id) from dish where flavor_id=#{id}")
    Long countByFlavor(Integer id);
    @Select("select count(id) from dish where category_id=#{id}")
    Long countByCategory(Integer id);
}
