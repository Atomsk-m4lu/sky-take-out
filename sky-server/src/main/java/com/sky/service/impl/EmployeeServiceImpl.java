package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    //员工修改密码
    @Override
    public void editPassword(PasswordEditDTO passwordEditDTO) {
        //判断旧密码是否正确
        String oldPassword = passwordEditDTO.getOldPassword();
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());

        //根据id查询数据库中的数据
        Employee employee = employeeMapper.getById(BaseContext.getCurrentId());
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if (!oldPassword.equals(employee.getPassword())) {
            //旧密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }else{
            //新密码
            String newPassword = passwordEditDTO.getNewPassword();
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
            //设置新密码
            employee.setPassword(newPassword);
            //设置更新时间
            //employee.setUpdateTime(LocalDateTime.now());
            //设置更新人id
            //employee.setUpdateUser(BaseContext.getCurrentId());
            //调用mapper更新
            employeeMapper.updatePasswordById(employee);
        }
    }
    //根据id查询员工信息
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    //员工分页查询
    @Override
    public PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //使用pagehelper分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        //调用mapper分页查询, PageHelper会自动将结果包装成Page对象
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    //员工新增
    @Override
    public void add(EmployeeDTO employeeDTO) {
        //创建员工对象
        Employee employee = new Employee();
        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置密码，使用默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置创建时间
        //employee.setCreateTime(LocalDateTime.now());
        //设置更新时间
        //employee.setUpdateTime(LocalDateTime.now());
        //设置状态
        employee.setStatus(StatusConstant.ENABLE);
        //设置创建人id
        //employee.setCreateUser(BaseContext.getCurrentId());
        //设置更新人id
        //employee.setUpdateUser(BaseContext.getCurrentId());
        //调用mapper新增
        employeeMapper.insert(employee);
    }

    //员工修改
    @Override
    public void edit(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        //employee.setUpdateUser(BaseContext.getCurrentId());
        //employee.setUpdateTime(LocalDateTime.now());
        //调用mapper修改
        employeeMapper.updateById(employee);
    }

    //员工状态修改
    @Override
    public void updateStatus(Long id, Integer status) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.updateStatusById(employee);
    }

}