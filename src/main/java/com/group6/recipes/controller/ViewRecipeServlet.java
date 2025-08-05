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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewRecipe")
public class ViewRecipeServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final RecipeIngredientDAOImpl recipeIngredientDAO = new RecipeIngredientDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

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
            // Check if the current user is the owner of this recipe
            boolean isOwner = (userId != null && userId.equals(recipe.getUserId()));

            req.setAttribute("recipe", recipe);
            req.setAttribute("ingredientsList", ingredientsList);
            req.setAttribute("isOwner", isOwner);
            req.setAttribute("currentUserId", userId);
            req.getRequestDispatcher("/view_recipe.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
