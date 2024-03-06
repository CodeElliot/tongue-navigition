package com.tongue.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class StepDTO implements Serializable {
    private Long id;
    private String content;
    private Long dishId;
}
