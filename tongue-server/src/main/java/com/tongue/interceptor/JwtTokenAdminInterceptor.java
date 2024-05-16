package com.tongue.interceptor;

import com.tongue.constant.JwtClaimsConstant;
import com.tongue.context.BaseContext;
import com.tongue.exception.RedisTokenNotFoundException;
import com.tongue.properties.JwtProperty;
import com.tongue.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * jwt令牌校验
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperty jwtProperty;
    //注入一个redis操作对象
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 校验jwt
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        //判断当前拦截到的是Controller的方法还是其他资源
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //1.从请求头获取令牌
        String token = request.getHeader(jwtProperty.getAdminTokenName());
        //2.校验令牌
        try{
            //从redis中获取令牌
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            if(operations.get(token)==null){
                //未获取到
                throw new RedisTokenNotFoundException();
            }
            log.info("jwt校验：{}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperty.getAdminSecretKey(), token);
            //获取当前操作用户的id，并存入线程
            Object empId =claims.get(JwtClaimsConstant.EMP_ID).toString();
            log.info("当前员工id：{}",empId);
            if(empId!=null){
                BaseContext.setCurrentId(empId);
            }
            //3.通过则放行
            return true;
        }catch (Exception exception){
            //4.不通过则设置状态码为401
            response.setStatus(401);
            return false;
        }
    }
}
