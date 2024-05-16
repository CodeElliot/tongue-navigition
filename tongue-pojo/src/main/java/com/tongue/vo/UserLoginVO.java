package com.tongue.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {
    private Long id;
    private String openid;
    private String token;
}
