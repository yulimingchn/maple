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
     * 按type查询所有的类目
     * @param type 类目类型
     * @return
     */
    List<Category> getCategoriesByType(@Param("type") Integer type);

    /**
     * 获取所有的分类
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

    /**
     * 查询单个类目
     * @param id
     * @return
     */
    Category getCategoryDetail(@Param("id") Integer id);
}
