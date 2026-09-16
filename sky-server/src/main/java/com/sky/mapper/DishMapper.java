package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @Insert("insert into dish(category_id, name, image, price, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{categoryId}, #{name}, #{image}, #{price}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    //分页查询菜品
    Page<Dish> page(DishPageQueryDTO dishPageQueryDTO);

    //根据id查询菜品
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    //根据分类id查询菜品
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> selectByCategoryId(Long categoryId);

    //批量删除菜品
    void deleteBatch(List<Long> ids);

    //修改菜品
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}