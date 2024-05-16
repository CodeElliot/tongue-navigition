package com.tongue.controller.admin;

import com.tongue.constant.JwtClaimsConstant;
import com.tongue.constant.MessageConstant;
import com.tongue.context.BaseContext;
import com.tongue.dto.EmployeeDTO;
import com.tongue.dto.EmployeeLoginDTO;
import com.tongue.dto.EmployeeRegisterDTO;
import com.tongue.dto.RepasswordDTO;
import com.tongue.entity.Employee;
import com.tongue.properties.JwtProperty;
import com.tongue.result.PageResult;
import com.tongue.result.Result;
import com.tongue.service.admin.EmployeeService;
import com.tongue.utils.JwtUtil;
import com.tongue.vo.EmployeeLoginVO;
import com.tongue.vo.EmployeeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工相关接口")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperty jwtProperty;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 登录
     * @param employeeLogin
     * @return
     */
    @Operation(summary = "员工登录" )
    @Parameter(name = "employeeLogin",description = "员工登录信息DTO")
    @PostMapping("/login")
    public  Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLogin){
        Employee employee=employeeService.login(employeeLogin);
        //验证通过，构造token,生成jwt令牌
        Map<String,Object> claim=new HashMap<>();
        claim.put(JwtClaimsConstant.EMP_ID,employee.getId());
        String token = JwtUtil.createJWT(jwtProperty.getAdminSecretKey(), jwtProperty.getAdminTtl(), claim);
        //向redis中添加token
        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        //时间与jwt的token保持一致
        operations.set(token,token,30, TimeUnit.MINUTES);
        //生成VO对象
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .ename(employee.getEname())
                .eimg(employee.getEimg())
                .account(employee.getAccount())
                .token(token)
                .build();
        return Result.success(MessageConstant.LOGIN_SUCCESS,employeeLoginVO);
    }

    /**
     * 员工注册：只有管理员有权添加员工
     * @param employeeRegisterDTO
     * @return
     */
    @Operation(summary = "员工注册" )
    @Parameter(name = "employeeRegisterDTO",description = "员工注册信息DTO")
    @PostMapping("/register")
    public Result register(@RequestBody EmployeeRegisterDTO employeeRegisterDTO){
        employeeService.register(employeeRegisterDTO);
        return Result.success(MessageConstant.REGISTRY_SUCCESS);
    }
    /**
     * 修改密码
     */
    @Operation(summary = "员工修改密码" )
    @Parameter(name = "RepasswordDTO",description = "密码请求DTO")
    @PutMapping("/repassword")
    public Result rePassword(@RequestBody RepasswordDTO repasswordDTO,@RequestHeader("token") String token){
        employeeService.repassword(repasswordDTO);
        //清除旧token
        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success(MessageConstant.PASSWORD_RESET_SUCCESS);
    }
    /**
     * 查询员工
     */
    @Operation(summary = "员工查询" )
    @GetMapping("/list")
    public Result<PageResult> list(Integer pageNumber,Integer pageSize,
                                         @RequestParam(required = false) String account,
                                         @RequestParam(required = false) String ename){
        PageResult pageResults=employeeService.list(pageNumber,pageSize,account,ename);
        return Result.success(MessageConstant.COMMON_RESULT,pageResults);
    }
    /**
     * 修改员工信息
     */
    @Operation(summary = "修改员工信息" )
    @PutMapping("/update")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        employeeService.update(employeeDTO);
        return Result.success(MessageConstant.COMMON_RESULT);
    }
    /**
     * 删除员工
     */
    @Operation(summary = "删除员工" )
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        employeeService.delete(id);
        return Result.success(MessageConstant.COMMON_RESULT);
    }
}
