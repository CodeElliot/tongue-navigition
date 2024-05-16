package com.tongue.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongue.constant.MessageConstant;
import com.tongue.context.BaseContext;
import com.tongue.dto.EmployeeDTO;
import com.tongue.dto.EmployeeLoginDTO;
import com.tongue.dto.EmployeeRegisterDTO;
import com.tongue.dto.RepasswordDTO;
import com.tongue.entity.Employee;
import com.tongue.exception.*;
import com.tongue.mapper.admin.EmployeeMapper;
import com.tongue.result.PageResult;
import com.tongue.service.admin.EmployeeService;
import com.tongue.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLogin
     */
    public Employee login(EmployeeLoginDTO employeeLogin) {
        //1.获取账户名与密码的md5加密形式
        String account = employeeLogin.getAccount();
        String password = employeeLogin.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //2.通过mapper查询并返回一个employee实体对象
        Employee employee = employeeMapper.getByAccount(account);
        //3.判断账户是否为空
        if (employee == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //4.判断密码是否为空并比对
        if (!password.equals(employee.getPassword())) {
            //不符
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //5.返回一个实体类用于之后的token
        return employee;
    }

    /**
     * 员工注册
     *
     * @param employeeRegisterDTO
     */
    public void register(EmployeeRegisterDTO employeeRegisterDTO) {
        String ename = employeeRegisterDTO.getEname();
        String account = employeeRegisterDTO.getAccount();
        //默认密码
        String password = "123456";

        Employee employee = employeeMapper.getByAccount(account);
        if (employee != null) {
            //有相同账户名
            throw new AccountIsExitedException(MessageConstant.ACCOUNT_IS_EXITED);
        }
        Employee emp = new Employee();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        BeanUtils.copyProperties(employeeRegisterDTO, emp);
        emp.setPassword(password);
        employeeMapper.add(emp);
    }

    /**
     * 修改密码
     *
     * @param repasswordDTO
     */
    public void repassword(RepasswordDTO repasswordDTO) {
        String account = repasswordDTO.getAccount();
        String password = repasswordDTO.getPassword();
        String repassword = repasswordDTO.getRepassword();
        String newpassword = repasswordDTO.getNewpassword();

        if (account == null || account.equals("")) {
            //账号为空
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(!StringUtils.hasLength(password)||!StringUtils.hasLength(repassword)||!StringUtils.hasLength(newpassword)){
            throw new InputNotNullException(MessageConstant.INPUT_NOT_NULL);
        }
        Employee employee = employeeMapper.getByAccount(account);
        if(employee==null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(!(employee.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8))))){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (!passwordIsLegal(password)) {
            throw new PasswordLengthErrorException(MessageConstant.PASSWORD_LENGTH_ERROR);
        }
        employee.setPassword(DigestUtils.md5DigestAsHex(newpassword.getBytes(StandardCharsets.UTF_8)));

        employeeMapper.update(employee);

    }

    /**
     * 按条件分页查询员工
     * @param pageNumber
     * @param pageSize
     * @param account
     * @param ename
     * @return
     */
    public PageResult list(Integer pageNumber, Integer pageSize, String account, String ename) {
        PageResult<EmployeeVO> pageResult=new PageResult<>();
        PageHelper.startPage(pageNumber,pageSize);
        Page<EmployeeVO> page=employeeMapper.list(account,ename);
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(page.getResult());
        return pageResult;
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    public void  update(EmployeeDTO employeeDTO) {
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setId(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

    /**
     * 删除员工
     * @param id
     */
    public void delete(Integer id) {
        employeeMapper.deleteById(id);
    }

    /**
     * 重置密码
     * @param id
     */
    public void resetPassword(Integer id){

    }
    /**
     * 判断密码长度是否合法
     * @param password
     * @return
     */
    private boolean passwordIsLegal(String password){
        if (password == null || password.equals("") || (password.length() < 5 || password.length() > 20)) {
            return false;
        }
        return true;
    }
}
