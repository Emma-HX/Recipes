// src/main/java/com/group6/recipes/dao/RecipesDAO.java
package com.group6.recipes.dao;

import com.group6.recipes.model.Recipe;
import com.group6.recipes.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipesDAOImpl implements RecipesDAO {
    /**
     * create a new recipe
     * @param recipe
     * @throws SQLException
     */
    public void addRecipe(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO recipes (user_id, title, description, prepSteps) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipe.getUserId());
            ps.setString(2, recipe.getTitle());
            ps.setString(3, recipe.getDescription());
            ps.setString(4, recipe.getPrepSteps());
            ps.executeUpdate();
        }
    }

    /**
     * get all recipes list
     * @return Recipes list
     */
    public List<Recipe> getAllRecipes() throws SQLException {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Recipe r = new Recipe();
                r.setRecipeId(rs.getInt("recipe_id"));
                r.setUserId(rs.getInt("user_id"));
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));
                r.setPrepSteps(rs.getString("prepSteps"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                r.setUpdatedAt(rs.getTimestamp("updated_at"));
                r.setImagePath(rs.getString("image_path"));
                list.add(r);
            }
        }
        return list;
    }

    /**
     * get recipe by id
     * @param recipeId
     * @return
     * @throws SQLException
     */
    public Recipe getRecipeById(int recipeId) throws SQLException {
        String sql = "SELECT * FROM recipes WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Recipe r = new Recipe();
                    r.setRecipeId(rs.getInt("recipe_id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setTitle(rs.getString("title"));
                    r.setDescription(rs.getString("description"));
                    r.setPrepSteps(rs.getString("prepSteps"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    r.setUpdatedAt(rs.getTimestamp("updated_at"));
                    r.setImagePath(rs.getString("image_path"));
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * update recipe
     * @param recipe
     * @throws SQLException
     */
    public void updateRecipe(Recipe recipe) throws SQLException {
        String sql = "UPDATE recipes SET title=?, description=?, prepSteps=?, image_path=? WHERE recipe_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getDescription());
            ps.setString(3, recipe.getPrepSteps());
            ps.setString(4, recipe.getImagePath());
            ps.setInt(5, recipe.getRecipeId());
        //    ps.setInt(6, recipe.getUserId());
            ps.executeUpdate();
        }
    }

    /**
     * delete recipe
     * @param recipeId
     * @throws SQLException
     */
    public void deleteRecipe(int recipeId) throws SQLException {
        RecipeIngredientDAOImpl recipeIngredientDAO = new RecipeIngredientDAOImpl();
        RecipeCategoryDAOImpl recipeCategoryDAO = new RecipeCategoryDAOImpl();
        recipeIngredientDAO.deleteByRecipe(recipeId);
        recipeCategoryDAO.deleteByRecipe(recipeId);
        String sql = "DELETE FROM recipes WHERE recipe_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.executeUpdate();
        }
    }

    /**
     * get recipes by userid
     * @param userId
     * @return
     * @throws SQLException
     */
    public List<Recipe> getRecipesByUser(int userId) throws SQLException {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE user_id=? ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Recipe r = new Recipe();
                    r.setRecipeId(rs.getInt("recipe_id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setTitle(rs.getString("title"));
                    r.setDescription(rs.getString("description"));
                    r.setPrepSteps(rs.getString("prepSteps"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    r.setUpdatedAt(rs.getTimestamp("updated_at"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    @Override
    public List<Recipe> getRecipesByCategory(int categoryId) throws SQLException {
        List<Recipe> list = new ArrayList<>();
        String sql = "select *\n" +
                "from recipes as r\n" +
                "inner join recipe_categories as rc on r.recipe_id = rc.recipe_id\n" +
                "where rc.category_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Recipe r = new Recipe();
                    r.setRecipeId(rs.getInt("recipe_id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setTitle(rs.getString("title"));
                    r.setDescription(rs.getString("description"));
                    r.setPrepSteps(rs.getString("prepSteps"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    r.setUpdatedAt(rs.getTimestamp("updated_at"));
                    r.setImagePath(rs.getString("image_path"));
                    list.add(r);
                }
            }
        }
        return list;
    }
}