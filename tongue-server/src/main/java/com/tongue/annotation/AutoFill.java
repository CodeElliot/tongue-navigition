package com.tongue.annotation;

import com.tongue.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 自动添加相关数据，用于标识某个方法需要进行功能字段自动填充处理
 */
public @interface AutoFill {
    OperationType value();
}
