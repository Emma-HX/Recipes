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

    }

    @Override
    public List<RecipeIngredient> getIngredientsByRecipe(int recipeId) throws SQLException{
        List<RecipeIngredient> list = new ArrayList<>();
        String sql = "SELECT ri.recipe_id, i.name, ri.quantity, units.unit_name\n" +
                "FROM recipe_ingredients as ri\n" +
                "INNER JOIN ingredients as i ON ri.ingredient_id = i.ingredient_id\n" +
                "INNER JOIN units ON ri.unit_id = units.unit_id\n" +
                "WHERE ri.recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setRecipeId(rs.getInt("ri.recipe_id"));
                    ri.setIngredientName(rs.getString("i.name"));
                    ri.setUnitName(rs.getString("units.unit_name"));
                    ri.setQuantity(rs.getInt("ri.quantity"));
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
