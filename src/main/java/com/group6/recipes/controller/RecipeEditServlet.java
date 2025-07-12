// src/main/java/com/group6/recipes/controller/RecipeEditServlet.java
package com.group6.recipes.controller;

import com.group6.recipes.dao.RecipesDAOImpl;
import com.group6.recipes.model.Recipe;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/editRecipe")
public class RecipeEditServlet extends HttpServlet {
    private RecipesDAOImpl recipesDAO = new RecipesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recipeId = Integer.parseInt(req.getParameter("id"));
        try {
            Recipe recipe = recipesDAO.getRecipeById(recipeId);
            req.setAttribute("recipe", recipe);
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