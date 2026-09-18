package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    //新增菜品
    @PostMapping()
    public Result<String>  insertDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        dishService.insertDish(dishDTO);

        //删除redis缓存中的菜品缓存
        String key = "dish_" + dishDTO.getCategoryId();
        cleanRedisCache(key);

        return Result.success("新增成功");
    }

    //菜品分页查询
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    //根据id查询菜品
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    //根据分类id查询菜品
    @GetMapping("list")
    public Result<List<Dish>> listByCategoryId(@RequestParam Long categoryId){
        log.info("根据分类id查询菜品：{}", categoryId);
        List<Dish> dishList = dishService.listByCategoryId(categoryId);
        return Result.success(dishList);
    }

    //批量删除菜品
    @DeleteMapping()
    public Result<String> deleteBatch(@RequestParam List<Long> ids){
        log.info("批量删除菜品：{}", ids);
        dishService.deleteBatch(ids);

        //删除redis缓存中的菜品缓存
        cleanRedisCache("dish_*");

        return Result.success();
    }

    //修改菜品
    @PutMapping()
    public Result<String> updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //删除redis缓存中的菜品缓存
        cleanRedisCache("dish_*");

        return Result.success();
    }

    //菜品起售、停售
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("菜品起售、停售：{}, id: {}", status, id);
        dishService.startOrStop(status, id);

        //删除redis缓存中的菜品缓存
        cleanRedisCache("dish_*");

        return Result.success();
    }

    private void cleanRedisCache(String pattern){
        Set key = redisTemplate.keys(pattern);
        redisTemplate.delete(key);
    }
}