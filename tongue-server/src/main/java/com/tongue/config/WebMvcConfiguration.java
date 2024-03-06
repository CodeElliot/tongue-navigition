package com.tongue.config;

import com.tongue.interceptor.JwtTokenAdminInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，配置web层组件
 */
@Configuration
@Slf4j
//WebMvcConfigurationSupport WebMvcConfigurer
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars");
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
    /**
     * 自定义响应拦截器
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry){
        log.info("开始注册管理端响应拦截器");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }


}
