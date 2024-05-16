package com.tongue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection implements Serializable {
    private Long id;
    private Long userId;
    private Long dishId;
}
