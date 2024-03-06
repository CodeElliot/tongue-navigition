package com.tongue.vo;

import lombok.Data;

import java.time.LocalDateTime;

//返回给前端的菜品查询信息
@Data
public class DishVO {
    private Long id;
    private String dname;
    private String dimg;
    private String cname;//菜品分类名
    private Integer categoryId;
    private Integer flavorId;
    private String fname;//口味名：需要联表查询
    private String step;
    private String ingredient;
    private String description;
    private Long click;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createName;
}
