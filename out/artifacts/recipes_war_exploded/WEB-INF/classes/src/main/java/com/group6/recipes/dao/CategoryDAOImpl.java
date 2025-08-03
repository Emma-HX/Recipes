package com.group6.recipes.dao;

import com.group6.recipes.model.Category;
import com.group6.recipes.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public void addCategory(Category category) {

    }

    @Override
    public Category getCategoryById(int categoryId) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                list.add(category);
            }
        }
        return list;
    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public void deleteCategory(int categoryId) {

    }
}
