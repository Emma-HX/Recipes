package com.group6.recipes.dao;

import com.group6.recipes.model.RecipeIngredient;
import java.util.List;

public interface RecipeIngredientDAO {
    public void addRecipeIngredient(RecipeIngredient ri);
    public List<RecipeIngredient> getIngredientsByRecipe(int recipeId);
    public void deleteByRecipe(int recipeId);
} 