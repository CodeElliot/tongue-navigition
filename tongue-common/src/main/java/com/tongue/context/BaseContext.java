package com.tongue.context;

//用于对线程进行操作
public class BaseContext {
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Object id) {
        threadLocal.set(id);
    }
    public static <T> T getCurrentId(){
        return (T) threadLocal.get();
    }
    public static void removeCurrentId(){
        threadLocal.remove();
    }
}
