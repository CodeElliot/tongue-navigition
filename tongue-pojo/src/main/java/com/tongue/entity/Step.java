package com.tongue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Step implements Serializable {
    private Long id;
    private String content;
    private Long dishId;
}
