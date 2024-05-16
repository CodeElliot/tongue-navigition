package com.tongue.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    private Integer code;//编码：1---成功，0----失败
    private String msg;
    private T data;
    public static <T> Result<T> success(String msg){
        Result<T> result = new Result<>();
        result.code=1;
        result.msg=msg;
        return result;
    }public static <T> Result<T> success(String msg,T data){
        Result<T> result = new Result<>();
        result.data=data;
        result.msg=msg;
        result.code=1;
        return result;
    }
    public static <T> Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.code=0;
        result.msg=msg;
        return result;
    }
}
