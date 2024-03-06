package com.tongue.mapper.admin;

import com.tongue.dto.StepDTO;
import com.tongue.entity.Step;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StepMapper {
    /**
     * 添加步骤
     * @param step
     */
    @Insert("insert into step (content, dish_id) VALUES (#{content},#{dishId})")
    void addStep(Step step);

    /**
     * 根据dishId删除菜品
     * @param id
     */
    @Delete("delete from step where dish_id=#{id}")
    void deleteByDishId(Long id);

    void update(Step step);
}
