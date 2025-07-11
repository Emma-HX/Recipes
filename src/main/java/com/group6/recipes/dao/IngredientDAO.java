package com.group6.recipes.dao;

import com.group6.recipes.model.Ingredient;
import java.util.List;

public interface IngredientDAO {
    public void addIngredient(Ingredient ingredient);
    public Ingredient getIngredientById(int ingredientId) ;
    public List<Ingredient> getAllIngredients() ;
    public void updateIngredient(Ingredient ingredient);
    public void deleteIngredient(int ingredientId);
} 