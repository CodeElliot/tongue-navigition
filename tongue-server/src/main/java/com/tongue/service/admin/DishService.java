package com.tongue.service.admin;

import com.tongue.dto.DishDTO;
import com.tongue.dto.StepDTO;
import com.tongue.entity.Category;
import com.tongue.entity.Flavor;
import com.tongue.result.PageResult;

import java.util.List;

public interface DishService {
    Long addDish(DishDTO dishDTO);

    PageResult list(Integer pageNumber, Integer pageSize, String dname, Integer categoryId, Integer flavorId, String ingredient,String openid);

    void update(DishDTO dishDTO);

    void delete(Long id);

    List<Flavor> getFlavors();

    List<Category> getCategories();

    void addDishByUser(DishDTO dishDTO);
}
