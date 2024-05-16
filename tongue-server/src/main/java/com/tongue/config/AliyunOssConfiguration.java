package com.tongue.config;

import com.tongue.properties.AliyunOssProperty;
import com.tongue.utils.AliyunOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AliyunOssConfiguration {
    @Bean
    @ConditionalOnMissingBean
   public AliyunOssUtil aliyunOssUtil(AliyunOssProperty aliyunOssProperty){
       log.info("开始创建阿里云文件上传工具类对象");
       return new AliyunOssUtil(aliyunOssProperty.getEndpoint(),aliyunOssProperty.getBucketName());
   }

}
