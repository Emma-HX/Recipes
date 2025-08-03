package com.group6.recipes.businesslayer;

import com.group6.recipes.dao.RecipesDAO;
import com.group6.recipes.dao.RecipesDAOImpl;
import com.group6.recipes.model.Recipe;

import java.sql.SQLException;
import java.util.List;

public class RecipeBusiness {

    private RecipesDAO recipesDAO;

    public RecipeBusiness() {
        recipesDAO = new RecipesDAOImpl();
    }

    public List<Recipe> searchRecipes(String keyword, String category) throws SQLException {
        return recipesDAO.searchRecipes(keyword, category);
    }
}
