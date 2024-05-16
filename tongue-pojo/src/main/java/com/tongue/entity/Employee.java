package com.tongue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    private Long id;
    private String ename;
    private String eimg;
    private String account;
    private String password;
}
