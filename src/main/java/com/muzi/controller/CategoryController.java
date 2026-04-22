package com.muzi.controller;

import com.muzi.annotation.Authority;
import com.muzi.common.Result;
import com.muzi.entity.AuthorityType;
import com.muzi.entity.Category;
import com.muzi.service.CategoryService;
import com.muzi.utils.BaseApi;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /*
    查询
    */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @GetMapping
    public Result findAll() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    /*
    保存
    */
    @PostMapping
    public Result save(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return Result.success();
    }

    /**
     *  新增下级分类 + 上下级分类关联
     *
     * @param category 下级分类
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Category category) {
        categoryService.add(category);
        return BaseApi.success();
    }

    @Authority(AuthorityType.requireAuthority)
    @PutMapping
    public Result update(@RequestBody Category category) {
        try {
            boolean result = categoryService.updateById(category);
            if (result) {
                return Result.success();
            } else {
                return Result.error("500", "更新失败，请检查数据是否正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "更新失败：" + e.getMessage());
        }
    }


    /**
     * 删除分类
     *
     * @param id id
     * @return 结果
     */
    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return categoryService.delete(id);
    }





}
