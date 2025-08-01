package com.group6.recipes.controller;

import com.group6.recipes.businesslayer.RecipeBusiness;
import com.group6.recipes.model.Recipe;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchRecipeServlet extends HttpServlet {

    private final RecipeBusiness recipeBusiness = new RecipeBusiness();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");

        try {
            List<Recipe> recipes = recipeBusiness.searchRecipes(keyword, category);

            request.setAttribute("recipes", recipes);
            request.setAttribute("keyword", keyword);
            request.setAttribute("selectedCategory", category);

            RequestDispatcher dispatcher = request.getRequestDispatcher("results.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
