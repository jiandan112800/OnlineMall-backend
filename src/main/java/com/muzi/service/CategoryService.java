package com.muzi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.entity.Category;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.entity.IconCategory;
import com.muzi.mapper.CategoryMapper;
import com.muzi.mapper.IconCategoryMapper;
import com.muzi.utils.BaseApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private IconCategoryMapper iconCategoryMapper;

    /**
     *  新增下级分类 + 上下级分类关联
     *
     * @param category 下级分类
     */
    public void add(Category category) {
        save(category);
        IconCategory iconCategory = new IconCategory();
        iconCategory.setCategoryId(category.getId());
        iconCategory.setIconId(category.getIconId());
        iconCategoryMapper.insert(iconCategory);
    }

    /**
     * 查询所有分类，并设置iconId
     *
     * @return 分类列表
     */
    @Override
    public List<Category> list() {
        List<Category> categories = super.list();
        // 为每个分类设置iconId
        for (Category category : categories) {
            IconCategory iconCategory = iconCategoryMapper.selectOne(
                    new QueryWrapper<IconCategory>().eq("category_id", category.getId())
            );
            if (iconCategory != null) {
                category.setIconId(iconCategory.getIconId());
            }
        }
        return categories;
    }

    /**
     * 根据ID查询分类，并设置iconId
     *
     * @param id 分类ID
     * @return 分类
     */
    public Category getCategoryById(Long id) {
        Category category = super.getById(id);
        if (category != null) {
            IconCategory iconCategory = iconCategoryMapper.selectOne(
                    new QueryWrapper<IconCategory>().eq("category_id", id)
            );
            if (iconCategory != null) {
                category.setIconId(iconCategory.getIconId());
            }
        }
        return category;
    }

    /**
     * 更新分类，如果iconId改变则更新关联关系
     *
     * @param category 分类
     * @return 是否成功
     */
    @Override
    public boolean updateById(Category category) {
        if (category.getId() == null) {
            throw new RuntimeException("分类ID不能为空");
        }
        
        // 先查询旧的关联关系
        IconCategory oldIconCategory = iconCategoryMapper.selectOne(
                new QueryWrapper<IconCategory>().eq("category_id", category.getId())
        );
        
        // 更新分类基本信息（只更新name字段，因为iconId不在category表中）
        boolean result = super.updateById(category);
        
        if (!result) {
            throw new RuntimeException("更新分类基本信息失败，分类ID可能不存在");
        }
        
        // 如果iconId不为空，更新关联关系
        if (category.getIconId() != null) {
            if (oldIconCategory != null) {
                // 如果iconId改变了，先删除旧关联，再创建新关联
                if (!oldIconCategory.getIconId().equals(category.getIconId())) {
                    iconCategoryMapper.delete(
                            new QueryWrapper<IconCategory>().eq("category_id", category.getId())
                    );
                    IconCategory newIconCategory = new IconCategory();
                    newIconCategory.setCategoryId(category.getId());
                    newIconCategory.setIconId(category.getIconId());
                    iconCategoryMapper.insert(newIconCategory);
                }
            } else {
                // 如果没有关联，创建新关联
                IconCategory iconCategory = new IconCategory();
                iconCategory.setCategoryId(category.getId());
                iconCategory.setIconId(category.getIconId());
                iconCategoryMapper.insert(iconCategory);
            }
        } else if (oldIconCategory != null) {
            // 如果iconId为空，删除关联
            iconCategoryMapper.delete(
                    new QueryWrapper<IconCategory>().eq("category_id", category.getId())
            );
        }
        
        return result;
    }

    /**
     * 删除分类
     *
     * @param id id
     * @return 结果
     */
    public Map<String, Object> delete(Long id) {
        // 删除关联
        iconCategoryMapper.delete(
                new QueryWrapper<IconCategory>().eq("category_id", id)
        );
        // 删除下级分类
        removeById(id);
        return BaseApi.success();
    }
}
