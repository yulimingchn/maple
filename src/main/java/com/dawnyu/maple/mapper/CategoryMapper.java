package com.dawnyu.maple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dawnyu.maple.bean.Category;

import java.util.List;

/**
 *  @author dawnyu
 */
@Mapper
public interface CategoryMapper {
    List<Category> getAllCategories();

    int deleteCategoryByIds(@Param("ids") String[] ids);

    int updateCategoryById(Category category);

    int addCategory(Category category);
}
