package com.group6.recipes.dao;

import com.group6.recipes.model.RecipeIngredient;

import java.sql.SQLException;
import java.util.List;

public interface RecipeIngredientDAO {
    public void addRecipeIngredient(RecipeIngredient ri) throws SQLException;
    public List<RecipeIngredient> getIngredientsByRecipe(int recipeId) throws SQLException;
    public void deleteByRecipe(int recipeId) throws SQLException;
} 