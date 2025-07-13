package com.group6.recipes.dao;

import com.group6.recipes.model.RecipeCategory;

import java.sql.SQLException;
import java.util.List;

public interface RecipeCategoryDAO {
    public void addRecipeCategory(RecipeCategory rc) throws SQLException;
    public void updateRecipeCategory(RecipeCategory rc) throws SQLException;
    public List<RecipeCategory> getCategoriesByRecipe(int recipeId) throws SQLException;
    public void deleteByRecipe(int recipeId) throws SQLException;
} 