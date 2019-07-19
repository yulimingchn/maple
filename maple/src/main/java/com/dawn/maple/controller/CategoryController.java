package com.dawn.maple.controller;

import com.dawn.maple.bean.Category;
import com.dawn.maple.bean.RespBean;
import com.dawn.maple.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dawnyu
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public List<Category> getArticleCategories() {
        return categoryService.getArticleCategories();
    }

    @RequestMapping(value = "/consume", method = RequestMethod.GET)
    public List<Category> getConsumeCategories() {
        return categoryService.getConsumeCategories();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = categoryService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewCate(Category category) {
        int result = categoryService.addCategory(category);
        if (result == 1) {
            return new RespBean("success", "添加成功!");
        }
        return new RespBean("error", "添加失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCate(Category category) {
        int i = categoryService.updateCategoryById(category);
        if (i == 1) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}
