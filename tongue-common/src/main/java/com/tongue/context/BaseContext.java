package com.tongue.context;

//用于对线程进行操作
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
    public static void removeCurrentId(){
        threadLocal.remove();
    }
}
