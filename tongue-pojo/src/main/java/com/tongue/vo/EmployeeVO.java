package com.tongue.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployeeVO implements Serializable {
    private Long id;
    private String ename;
    private String eimg;
    private String account;
}
