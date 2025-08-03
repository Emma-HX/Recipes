package com.group6.recipes.dao;

import com.group6.recipes.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    public void addCategory(Category category);
    public Category getCategoryById(int categoryId) ;
    public List<Category> getAllCategories() throws SQLException;
    public void updateCategory(Category category);
    public void deleteCategory(int categoryId) ;
} 