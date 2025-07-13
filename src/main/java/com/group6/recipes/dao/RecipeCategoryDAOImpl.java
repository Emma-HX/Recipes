package com.group6.recipes.dao;

import com.group6.recipes.model.RecipeCategory;
import com.group6.recipes.model.RecipeIngredient;
import com.group6.recipes.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeCategoryDAOImpl implements RecipeCategoryDAO {

    @Override
    public void addRecipeCategory(RecipeCategory rc) throws SQLException{
        String sql = "INSERT INTO recipe_categories (recipe_id, category_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rc.getRecipeId());
            ps.setInt(2, rc.getCategoryId());
            ps.executeUpdate();
        }
    }

    @Override
    public void updateRecipeCategory(RecipeCategory rc) throws SQLException {
        deleteByRecipe(rc.getRecipeId());
        addRecipeCategory(rc);
    }

    @Override
    public List<RecipeCategory> getCategoriesByRecipe(int recipeId) throws SQLException{
        List<RecipeCategory> list = new ArrayList<>();
        String sql = "SELECT rc.recipe_id, c.category_id, c.name\n" +
                "FROM recipe_categories as rc\n" +
                "INNER JOIN categories as c ON rc.category_id = c.category_id\n" +
                "WHERE rc.recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecipeCategory rc = new RecipeCategory();
                    rc.setRecipeId(rs.getInt("rc.recipe_id"));
                    rc.setCategoryId(rs.getInt("c.category_id"));
                    rc.setCategoryName(rs.getString("c.name"));
                    list.add(rc);
                }
            }
        }
        return list;
    }

    @Override
    public void deleteByRecipe(int recipeId) throws SQLException {
        String sql = "DELETE FROM recipe_categories WHERE recipe_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.executeUpdate();
        }
    }
}
