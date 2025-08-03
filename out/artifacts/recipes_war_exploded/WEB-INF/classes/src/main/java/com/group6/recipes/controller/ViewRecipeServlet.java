package com.group6.recipes.controller;

import com.group6.recipes.dao.RecipeIngredientDAOImpl;
import com.group6.recipes.dao.RecipesDAO;
import com.group6.recipes.dao.RecipesDAOImpl;
import com.group6.recipes.model.Recipe;

import com.group6.recipes.model.RecipeIngredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewRecipe")
public class ViewRecipeServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final RecipeIngredientDAOImpl recipeIngredientDAO = new RecipeIngredientDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("recipes");
            return;
        }
        try {
            int recipeId = Integer.parseInt(idStr);
            Recipe recipe = recipesDAO.getRecipeById(recipeId);
            List<RecipeIngredient> ingredientsList = recipeIngredientDAO.getIngredientsByRecipe(recipe.getRecipeId());
            if (recipe == null || ingredientsList == null) {
                resp.sendRedirect("recipes");
                return;
            }
            req.setAttribute("recipe", recipe);
            req.setAttribute("ingredientsList", ingredientsList);
            req.getRequestDispatcher("/view_recipe.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
