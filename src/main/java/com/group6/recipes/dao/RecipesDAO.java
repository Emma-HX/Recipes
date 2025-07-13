package com.group6.recipes.dao;

import com.group6.recipes.model.Recipe;

import java.sql.SQLException;
import java.util.List;

public interface RecipesDAO {
    public void addRecipe(Recipe recipe) throws SQLException;
    public List<Recipe> getAllRecipes() throws SQLException;
    public Recipe getRecipeById(int recipeId) throws SQLException;
    public void updateRecipe(Recipe recipe) throws SQLException;
    public void deleteRecipe(int recipeId) throws SQLException;
    public List<Recipe> getRecipesByUser(int userId) throws SQLException;
    public List<Recipe> getRecipesByCategory(int categoryId) throws SQLException;
}
