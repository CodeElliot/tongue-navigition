package com.tongue.constant;

public class MessageConstant {
    public static final String COMMON_RESULT="操作成功";
    //员工相关
    public static final String LOGIN_SUCCESS="登录成功";
    public static final String LOGIN_ERROE="登录失败";
    public static final String REGISTRY_SUCCESS="注册成功";
    public static final String REGISTRY_ERROE="注册失败";
    public static final String ACCOUNT_NOT_FOUND="账号不存在";
    public static final String PASSWORD_ERROR="密码错误";
    public static final String PASSWORD_LENGTH_ERROR="密码长度不符合要求";
    public static final String PASSWORD_RESET_SUCCESS="密码修改完成";
    public static final String INPUT_NOT_NULL="输入不能为空";
    public static final String PASSWORD_IS_NOT_SAME="两次密码不一致";

    public static final String ACCOUNT_IS_EXITED="账号已存在";
    //菜品相关
    public static final String DISH_ADD_SUCCESS="菜品添加成功";
    public static final String STEP_ADD_SUCCESS="步骤添加成功";
    public static final String DNAME_IS_NULL="菜品名不能为空";
    public static final String CATEGORY_IS_NULL="分类不能为空";
    public static final String FLAVOR_IS_NULL="口味不能为空";
    public static final String INGREDIENT_IS_NULL="口味不能为空";
    public static final String DISH_NOT_FOUND="未找到相关菜品，试着创建一个吧";
    public static final String FLAVOR_NOT_FOUND="请选择正确的口味";
    public static final String CATEGORY_NOT_FOUND="请选择正确的分类";
    public static final String STEP_TOO_LONG="步骤描述过长，请简略描述";
    //文件相关
    public static final String FILE_UPLOAD_SUCCESS="文件上传成功";
    //redis相关
    public static final String REDIS_TOKEN_NOT_FOUND="未在redis中找到匹配到token";

}
