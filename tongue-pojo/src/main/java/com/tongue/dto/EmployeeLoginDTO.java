package com.tongue.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@Data
public class EmployeeLoginDTO implements Serializable {
    private String account;
    private String password;
}
