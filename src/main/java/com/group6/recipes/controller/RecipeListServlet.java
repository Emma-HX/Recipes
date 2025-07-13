package com.group6.recipes.controller;

import com.group6.recipes.dao.CategoryDAOImpl;
import com.group6.recipes.dao.RecipesDAOImpl;
import com.group6.recipes.model.Category;
import com.group6.recipes.model.Recipe;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/recipesList")
public class RecipeListServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Recipe> recipes = new ArrayList<>();
            List<Category> categories = categoryDAO.getAllCategories();

            req.setAttribute("categories", categories);
            int categoryId = Integer.parseInt(req.getParameter("categoryId") != null ? req.getParameter("categoryId") : "-1");
            if(categoryId != -1){
                recipes = recipesDAO.getRecipesByCategory(categoryId);
                req.setAttribute("recipes", recipes);
            }else{
                recipes = recipesDAO.getAllRecipes();
                req.setAttribute("recipes", recipes);
            }
            req.getRequestDispatcher("/recipes.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}