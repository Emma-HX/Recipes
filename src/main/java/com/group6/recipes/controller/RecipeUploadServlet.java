package com.group6.recipes.controller;

import com.group6.recipes.dao.*;
import com.group6.recipes.model.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/create_recipe")
public class RecipeUploadServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final UnitDAOImpl unitDAO = new UnitDAOImpl();
    private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    private final RecipeIngredientDAOImpl recipeIngredientDAO = new RecipeIngredientDAOImpl();
    private final RecipeCategoryDAOImpl recipeCategoryDAO = new RecipeCategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Unit> allUnits = unitDAO.getAllUnits();
            List<Category> allCategories = categoryDAO.getAllCategories();

            req.setAttribute("allUnits", allUnits);
            req.setAttribute("allCategories", allCategories);

            req.getRequestDispatcher("/create_recipe.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading data for recipe creation", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = 1; // Ajustar según autenticación

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String prepSteps = req.getParameter("prepSteps");
        String imageUrl = req.getParameter("imageUrl"); // URL enviada desde el formulario

        // Validar que la URL no sea demasiado larga
        if (imageUrl != null && imageUrl.length() > 200) {
            req.setAttribute("errorMessage", "The image URL is too long. Please use a new link (e.g., via TinyURL).");

            // Recargar listas para que el JSP funcione correctamente
            try {
                req.setAttribute("allUnits", unitDAO.getAllUnits());
                req.setAttribute("allCategories", categoryDAO.getAllCategories());
            } catch (SQLException e) {
                throw new ServletException("Error loading units and categories", e);
            }

            // Volver al formulario con mensaje de error
            req.getRequestDispatcher("/create_recipe.jsp").forward(req, resp);
            return; // detener procesamiento
        }

        Recipe recipe = new Recipe();
        recipe.setUserId(userId);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepSteps(prepSteps);
        recipe.setImagePath(imageUrl);  // Guardamos la URL directamente

        try {
            int recipeId = recipesDAO.addRecipe(recipe);
            if (recipeId == -1) {
                throw new ServletException("Failed to retrieve generated recipe ID.");
            }

            String[] categoryIds = req.getParameterValues("categories");
            if (categoryIds != null) {
                for (String cid : categoryIds) {
                    RecipeCategory rc = new RecipeCategory();
                    rc.setRecipeId(recipeId);
                    rc.setCategoryId(Integer.parseInt(cid));
                    recipeCategoryDAO.addRecipeCategory(rc);
                }
            }

            String[] names = req.getParameterValues("ingredientNames");
            String[] quantities = req.getParameterValues("quantities");
            String[] unitIds = req.getParameterValues("unitIds");
            if (names != null && quantities != null && unitIds != null) {
                for (int i = 0; i < names.length; i++) {
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setRecipeId(recipeId);
                    ri.setIngredientName(names[i]);
                    ri.setQuantity(Double.parseDouble(quantities[i]));
                    ri.setUnitId(Integer.parseInt(unitIds[i]));
                    recipeIngredientDAO.addRecipeIngredient(ri);
                }
            }

            req.setAttribute("successMessage", "Your recipe has been created successfully!");
            req.getRequestDispatcher("/recipe_created.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Error creating recipe", e);
        }
    }
}
