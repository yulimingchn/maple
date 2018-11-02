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
    /**
     * 获取所有的类目
     * @return
     */
    List<Category> getAllCategories();

    /**
     * 批量删除类目
     * @param ids
     * @return
     */
    int deleteCategoryByIds(@Param("ids") String[] ids);

    /**
     * 修改类目根据id
     * @param category
     * @return
     */
    int updateCategoryById(Category category);

    /**
     * 新增类目
     * @param category
     * @return
     */
    int addCategory(Category category);
}
