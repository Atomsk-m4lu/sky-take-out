package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

//员工服务接口
public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //修改密码
    void editPassword(PasswordEditDTO passwordEditDTO);

    //根据id查询员工
    Employee getById(Long id);

    //分页查询员工
    PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //新增员工
    void add(EmployeeDTO employeeDTO);

    //修改员工
    void edit(EmployeeDTO employeeDTO);

    //更新员工状态
    void updateStatus(Long id, Integer status);
}
