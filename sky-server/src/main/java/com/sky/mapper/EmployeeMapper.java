package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    //根据id查询员工
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    //分页查询员工
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //新增员工
    @Insert("insert into employee (username, password, name, phone, sex, id_number, status, create_time, update_time, create_user, update_user) values (#{username}, #{password}, #{name}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(OperationType.INSERT)
    void insert(Employee employee);

    //根据员工id更新员工信息
    @Update("update employee set username = #{username}, name = #{name}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(OperationType.UPDATE)
    void updateById(Employee employee);

    //根据员工id更新员工密码
    @Update("update employee set password = #{password}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(OperationType.UPDATE)
    void updatePasswordById(Employee employee);

    //根据员工id更新员工状态
    @Update("update employee set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(OperationType.UPDATE)
    void updateStatusById(Employee employee);
}