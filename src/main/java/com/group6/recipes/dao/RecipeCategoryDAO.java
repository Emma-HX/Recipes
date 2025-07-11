package com.group6.recipes.dao;

import com.group6.recipes.model.RecipeCategory;
import java.util.List;

public interface RecipeCategoryDAO {
    public void addRecipeCategory(RecipeCategory rc) ;
    public List<RecipeCategory> getCategoriesByRecipe(int recipeId);
    public void deleteByRecipe(int recipeId) ;
} 