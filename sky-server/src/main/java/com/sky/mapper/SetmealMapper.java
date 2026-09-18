package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    //新增套餐
    @Insert("insert into setmeal (name, price, category_id, description, image, status, create_time, update_time,create_user,update_user) values (#{name}, #{price}, #{categoryId}, #{description}, #{image}, #{status}, #{createTime}, #{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    //分页查询套餐
    Page<Setmeal> page(SetmealPageQueryDTO setmealPageQueryDTO);

    //批量删除套餐
    void deleteBatch(List<Long> ids);

    //修改套餐
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    //根据id查询套餐
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    //套餐起售、停售
    @Update("update setmeal set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(OperationType.UPDATE)
    void updateStatus(Setmeal setmeal);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}