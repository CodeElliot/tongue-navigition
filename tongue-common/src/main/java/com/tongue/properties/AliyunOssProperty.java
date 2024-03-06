package com.tongue.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tongue.oss")
@Data
public class AliyunOssProperty {
    private String bucketName;
    private String endpoint;
}
