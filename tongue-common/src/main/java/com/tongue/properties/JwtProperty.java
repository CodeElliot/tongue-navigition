package com.tongue.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component//该注解在springboot-starter-json依赖中
@ConfigurationProperties(prefix = "tongue.jwt")
@Data
public class JwtProperty {
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
