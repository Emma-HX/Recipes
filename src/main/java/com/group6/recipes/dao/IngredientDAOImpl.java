package com.group6.recipes.dao;

import com.group6.recipes.model.Ingredient;
import com.group6.recipes.model.Recipe;
import com.group6.recipes.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO {

    @Override
    public void addIngredient(Ingredient ingredient) {

    }

    @Override
    public Ingredient getIngredientById(int ingredientId) {
        return null;
    }

    @Override
    public List<Ingredient> getAllIngredients() throws SQLException {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM ingredients";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIngredientId(rs.getInt("ingredient_id"));
                ing.setName(rs.getString("name"));
                list.add(ing);
            }
        }
        return list;
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {

    }

    @Override
    public void deleteIngredient(int ingredientId) {

    }
}
