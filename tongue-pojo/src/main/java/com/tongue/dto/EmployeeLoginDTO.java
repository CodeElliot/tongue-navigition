package com.tongue.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import java.io.Serializable;

@Data
@Schema(description = "员工登录DTO")
public class EmployeeLoginDTO implements Serializable {
    @Schema(description = "账号名")
    private String account;
    @Schema(description = "密码")
    private String password;
}
