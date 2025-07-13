package com.group6.recipes.dao;

import com.group6.recipes.model.Ingredient;

import java.sql.SQLException;
import java.util.List;

public interface IngredientDAO {
    public void addIngredient(Ingredient ingredient);
    public Ingredient getIngredientById(int ingredientId) ;
    public List<Ingredient> getAllIngredients() throws SQLException;
    public void updateIngredient(Ingredient ingredient);
    public void deleteIngredient(int ingredientId);
} 