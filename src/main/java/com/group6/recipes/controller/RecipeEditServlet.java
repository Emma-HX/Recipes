// src/main/java/com/group6/recipes/controller/RecipeEditServlet.java
package com.group6.recipes.controller;

import com.group6.recipes.dao.*;
import com.group6.recipes.model.Category;
import com.group6.recipes.model.Ingredient;
import com.group6.recipes.model.Recipe;

import com.group6.recipes.model.Unit;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/editRecipe")
public class RecipeEditServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final IngredientDAOImpl ingredientDAO = new IngredientDAOImpl();
    private final UnitDAOImpl unitDAO = new UnitDAOImpl();
    private final CategoryDAOImpl CategoryDAO = new CategoryDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=========================doGet");
        int recipeId = Integer.parseInt(req.getParameter("id"));
        try {
            Recipe recipe = recipesDAO.getRecipeById(recipeId);
            List<Ingredient> allIngredients = ingredientDAO.getAllIngredients();
            List<Unit> allUnits = unitDAO.getAllUnits();
            List<Category> allCategories = CategoryDAO.getAllCategories();
            System.out.println("=================" + (recipe == null ? "null" : recipe.getRecipeId()));
            req.setAttribute("recipe", recipe);
            req.setAttribute("ingredients", allIngredients);
            req.setAttribute("units", allUnits);
            req.setAttribute("categories", allCategories);
            req.getRequestDispatcher("/edit_recipe.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int recipeId = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String prepSteps = req.getParameter("prepSteps");

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setUserId(userId);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepSteps(prepSteps);

        try {
            recipesDAO.updateRecipe(recipe);
            resp.sendRedirect("recipes");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}