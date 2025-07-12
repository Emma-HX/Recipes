// src/main/java/com/group6/recipes/controller/RecipeDeleteServlet.java
package com.group6.recipes.controller;

import com.group6.recipes.dao.RecipesDAOImpl;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteRecipe")
public class RecipeDeleteServlet extends HttpServlet {
    private RecipesDAOImpl recipesDAO = new RecipesDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Integer userId = (Integer) req.getSession().getAttribute("userId");
//        if (userId == null) {
//            resp.sendRedirect("login.jsp");
//            return;
//        }
        int recipeId = Integer.parseInt(req.getParameter("id"));
        try {
            recipesDAO.deleteRecipe(recipeId);

            resp.sendRedirect("recipesList");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}