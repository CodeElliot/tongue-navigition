package com.tongue.mapper.admin;

import com.github.pagehelper.Page;
import com.tongue.entity.Employee;
import com.tongue.vo.EmployeeVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where account=#{account}")
    Employee getByAccount(String account);
    @Insert("insert into employee (ename, account, password, eimg) VALUES (#{ename},#{account},#{password},#{eimg})")
    void add(Employee employee);

    void update(Employee employee);

    Page<EmployeeVO> list(String account, String ename);
    @Delete("delete from employee where id=#{id}")
    void deleteById(Integer id);
}
