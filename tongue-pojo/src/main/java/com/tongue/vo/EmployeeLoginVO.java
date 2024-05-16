package com.tongue.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EmployeeLoginVO implements Serializable {
    private Long id;
    private String ename;
    private String eimg;
    private String account;
    private String token;
}
