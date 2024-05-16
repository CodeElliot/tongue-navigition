package com.tongue.service.admin;

import com.tongue.dto.EmployeeDTO;
import com.tongue.dto.EmployeeLoginDTO;
import com.tongue.dto.EmployeeRegisterDTO;
import com.tongue.dto.RepasswordDTO;
import com.tongue.entity.Employee;
import com.tongue.result.PageResult;
import com.tongue.vo.EmployeeVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    Employee login(EmployeeLoginDTO employeeLogin);

    void register(EmployeeRegisterDTO employeeRegisterDTO);

    void repassword(RepasswordDTO repasswordDTO);

    PageResult list(Integer pageNumber, Integer pageSize, String account, String ename);

    void update(EmployeeDTO employeeDTO);

    void delete(Integer id);
}
