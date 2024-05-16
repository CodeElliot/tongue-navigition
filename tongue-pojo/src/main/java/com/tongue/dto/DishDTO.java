package com.tongue.dto;

import com.tongue.entity.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO implements Serializable {
    private Long id;
    private String dname;
    private String dimg;
    private Integer categoryId;
    private Integer flavorId;
    private String step;
    private String ingredient;
    private String description;
}
