package com.tongue.exception;

/**
 * Redis中未匹配到token
 */
public class RedisTokenNotFoundException extends BaseException{
    public RedisTokenNotFoundException(){};
    public RedisTokenNotFoundException(String msg){super(msg);}
}
