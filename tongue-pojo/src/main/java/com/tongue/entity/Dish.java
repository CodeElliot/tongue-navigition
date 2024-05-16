package com.tongue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish implements Serializable {
    private Long id;
    private String dname;
    private String dimg;
    private Integer categoryId;
    private Integer flavorId;
    private String ingredient;
    private String step;
    private String description;
    private Long click;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private String openid;
}
