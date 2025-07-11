// src/main/java/com/group6/recipes/controller/RecipeDeleteServlet.java
package com.group6.recipes.controller;

import com.group6.recipes.dao.RecipesDAOImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class RecipeDeleteServlet extends HttpServlet {
    private RecipesDAOImpl recipesDAO = new RecipesDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int recipeId = Integer.parseInt(req.getParameter("id"));
        try {
            recipesDAO.deleteRecipe(recipeId, userId);
            resp.sendRedirect("recipes");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}