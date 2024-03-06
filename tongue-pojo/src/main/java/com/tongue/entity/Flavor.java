package com.tongue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flavor implements Serializable {
    private Integer id;
    private String fname;
    private String description;
}
