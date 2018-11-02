package com.dawnyu.maple.service;

import com.dawnyu.maple.bean.Category;
import com.dawnyu.maple.constant.TypeConstant;
import com.dawnyu.maple.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author dawnyu
 */
@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = categoryMapper.deleteCategoryByIds(split);
        return result == split.length;
    }

    public int updateCategoryById(Category category) {
        return categoryMapper.updateCategoryById(category);
    }

    public int addCategory(Category category) {
        category.setCreateDate(new Timestamp(System.currentTimeMillis()));
        category.setType(TypeConstant.CATEGORY_TYPE_ARTICLE);
        return categoryMapper.addCategory(category);
    }
}
