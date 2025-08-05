package com.group6.recipes.controller;

import com.group6.recipes.dao.RecipeIngredientDAOImpl;
import com.group6.recipes.dao.RecipesDAOImpl;
import com.group6.recipes.model.Recipe;
import com.group6.recipes.model.RecipeIngredient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/recipeConfirmation")
public class RecipeConfirmationServlet extends HttpServlet {

    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final RecipeIngredientDAOImpl recipeIngredientDAO = new RecipeIngredientDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Read the recipeId from URL parameter
        int recipeId = Integer.parseInt(req.getParameter("id"));

        try {
            // Get recipe details
            Recipe recipe = recipesDAO.getRecipeById(recipeId);

            // Get ingredients for the recipe
            List<RecipeIngredient> ingredientsList = recipeIngredientDAO.getIngredientsByRecipe(recipeId);

            // Set attributes for the JSP
            req.setAttribute("recipe", recipe);
            req.setAttribute("ingredientsList", ingredientsList);

            // Forward to the confirmation JSP
            req.getRequestDispatcher("/recipe_confirmation.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error loading recipe confirmation page", e);
        }
    }
}
