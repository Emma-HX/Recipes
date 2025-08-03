package com.group6.recipes.dao;

import com.group6.recipes.model.Recipe;
import com.group6.recipes.model.RecipeIngredient;
import com.group6.recipes.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientDAOImpl implements RecipeIngredientDAO {

    @Override
    public void addRecipeIngredient(RecipeIngredient ri) throws SQLException {
        String sql = "INSERT INTO recipe_ingredients (recipe_id, ingredient_name, quantity, unit_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ri.getRecipeId());
            ps.setString(2, ri.getIngredientName());
            ps.setDouble(3, ri.getQuantity());
            ps.setInt(4, ri.getUnitId());
            ps.executeUpdate();
        }
    }

    @Override
    public void updateRecipeIngredient(RecipeIngredient ri) throws SQLException {
        deleteByRecipe(ri.getRecipeId());
        addRecipeIngredient(ri);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByRecipe(int recipeId) throws SQLException{
        List<RecipeIngredient> list = new ArrayList<>();
        String sql = "SELECT ri.recipe_id, ri.ingredient_name, ri.quantity, units.unit_id, units.unit_name\n" +
                "FROM recipe_ingredients as ri\n" +
                "INNER JOIN units ON ri.unit_id = units.unit_id\n" +
                "WHERE ri.recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setRecipeId(rs.getInt("ri.recipe_id"));
                    ri.setIngredientName(rs.getString("ri.ingredient_name"));
                    ri.setUnitName(rs.getString("units.unit_name"));
                    ri.setQuantity(rs.getInt("ri.quantity"));
                    ri.setUnitId(rs.getInt("units.unit_id"));
                    list.add(ri);
                }
            }
        }
        return list;
    }

    @Override
    public void deleteByRecipe(int recipeId) throws SQLException{
        String sql = "DELETE FROM recipe_ingredients WHERE recipe_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.executeUpdate();
        }
    }
}
