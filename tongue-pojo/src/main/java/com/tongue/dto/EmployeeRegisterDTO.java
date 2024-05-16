package com.tongue.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployeeRegisterDTO implements Serializable {
    private String account;
    private String ename;
}
