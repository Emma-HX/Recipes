package com.group6.recipes.controller;

import com.group6.recipes.dao.RecipesDAOImpl;
import com.group6.recipes.model.Recipe;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/recipesList")
public class RecipeListServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Recipe> recipes = recipesDAO.getAllRecipes();
            req.setAttribute("recipes", recipes);
            req.getRequestDispatcher("/recipes.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}