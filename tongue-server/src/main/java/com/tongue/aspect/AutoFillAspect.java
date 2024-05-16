package com.tongue.aspect;

import com.tongue.annotation.AutoFill;
import com.tongue.annotation.AutoFillByUser;
import com.tongue.constant.AutoFillConstant;
import com.tongue.context.BaseContext;
import com.tongue.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.Join;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.tongue.mapper.admin.*.*(..)) && @annotation(com.tongue.annotation.AutoFill)")
    public void autoFillPointCut() {
    }
    @Pointcut("execution(* com.tongue.mapper.admin.*.*(..))&&@annotation(com.tongue.annotation.AutoFillByUser)")
    public void autoFillByUserPointCut(){}

    /**
     * 前置通知，在通知中进行公共字段的填充
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开启公共字段填充。。。");
        //获取到当前被拦截的放上上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上的注解对象
        OperationType operationType = autoFill.value();//获取数据库操作类型

        //获取到当前被拦截方法上的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            return;
        }

        Object entity=args[0];
        //准备赋值的数据
        LocalDateTime currentDateTime=LocalDateTime.now();
        Long currentId= BaseContext.getCurrentId();
        //根据当前不同的操作类型，通过反射
        if (operationType==OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射为对象属性赋值
                setUpdateTime.invoke(entity,currentDateTime);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(operationType==OperationType.INSERT){
            try{
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime=entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setCreateUser=entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateUser=entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                Method setCreater = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Integer.class);
                //通过反射为对象赋值
                setCreateTime.invoke(entity,currentDateTime);
                setUpdateTime.invoke(entity,currentDateTime);
                setCreateUser.invoke(entity,currentId);
                setUpdateUser.invoke(entity,currentId);
                setCreater.invoke(entity, 0);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }

    }
    @Before("autoFillByUserPointCut()")
    public void autoFillByUser(JoinPoint joinPoint){
        log.info("开启用户信息自动");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFillByUser autoFillByUser = signature.getMethod().getAnnotation(AutoFillByUser.class);
        OperationType operationType = autoFillByUser.value();
        Object[] args = joinPoint.getArgs();
        if (args==null||args.length==0){
            return;
        }
        Object entity=args[0];
        LocalDateTime currentDateTime=LocalDateTime.now();
        String currentId=BaseContext.getCurrentId();
    }
}
