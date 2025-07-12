package com.group6.recipes.dao;

import com.group6.recipes.model.RecipeCategory;
import com.group6.recipes.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RecipeCategoryDAOImpl implements RecipeCategoryDAO {

    @Override
    public void addRecipeCategory(RecipeCategory rc) throws SQLException{

    }

    @Override
    public List<RecipeCategory> getCategoriesByRecipe(int recipeId) throws SQLException{
        return List.of();
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
