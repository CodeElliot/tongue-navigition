package com.tongue.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RepasswordDTO implements Serializable {
    private String account;
    private String password;
    private String repassword;
    private String newpassword;
}
