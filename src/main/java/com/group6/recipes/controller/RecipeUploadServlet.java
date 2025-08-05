package com.group6.recipes.controller;

import com.group6.recipes.dao.*;
import com.group6.recipes.model.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@MultipartConfig
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
        // Check if user is logged in
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String prepSteps = req.getParameter("prepSteps");

        // Validate main fields with personalized messages
        String errorMessage = null;
        if (title == null || title.trim().isEmpty()) {
            errorMessage = "Recipe name is required.";
        } else if (description == null || description.trim().isEmpty()) {
            errorMessage = "Description is required.";
        } else if (prepSteps == null || prepSteps.trim().isEmpty()) {
            errorMessage = "Preparation steps are required.";
        }

        // Validate ingredients only if main fields passed
        if (errorMessage == null) {
            String[] names = req.getParameterValues("ingredientNames");
            String[] quantities = req.getParameterValues("quantities");
            String[] unitIds = req.getParameterValues("unitIds");

            if (names == null || names.length == 0) {
                errorMessage = "At least one ingredient is required.";
            } else {
                for (int i = 0; i < names.length; i++) {
                    if (names[i] == null || names[i].trim().isEmpty()) {
                        errorMessage = "Ingredient name cannot be empty.";
                        break;
                    }
                    if (quantities[i] == null || quantities[i].trim().isEmpty()) {
                        errorMessage = "Quantity is required for all ingredients.";
                        break;
                    }
                    try {
                        double q = Double.parseDouble(quantities[i]);
                        if (q <= 0) {
                            errorMessage = "Quantity must be greater than zero.";
                            break;
                        }
                    } catch (NumberFormatException e) {
                        errorMessage = "Quantity must be a valid number.";
                        break;
                    }
                    if (unitIds[i] == null || unitIds[i].trim().isEmpty()) {
                        errorMessage = "Unit must be selected for all ingredients.";
                        break;
                    }
                }
            }
        }

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            try {
                req.setAttribute("allUnits", unitDAO.getAllUnits());
                req.setAttribute("allCategories", categoryDAO.getAllCategories());
            } catch (SQLException e) {
                throw new ServletException("Error loading data for recipe creation", e);
            }
            req.getRequestDispatcher("/create_recipe.jsp").forward(req, resp);
            return;
        }

        // Handle image upload
        Part imagePart = req.getPart("imageFile");
        String imagePath = null;

        String imagesDir = req.getServletContext().getRealPath("/images");
        File dir = new File(imagesDir);
        if (!dir.exists()) dir.mkdirs();

        if (imagePart != null && imagePart.getSize() > 0 &&
                imagePart.getSubmittedFileName() != null && !imagePart.getSubmittedFileName().isEmpty()) {

            String submittedFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int dot = submittedFileName.lastIndexOf('.');
            if (dot >= 0) ext = submittedFileName.substring(dot);

            String newFileName = UUID.randomUUID() + ext;
            File imageFile = new File(dir, newFileName);

            try (InputStream in = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(imageFile)) {

                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            }

            imagePath = "/images/" + newFileName;
        }

        // Create Recipe object and save to DB
        Recipe recipe = new Recipe();
        recipe.setUserId(userId);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepSteps(prepSteps);
        recipe.setImagePath(imagePath);

        try {
            int recipeId = recipesDAO.addRecipe(recipe);

            // Save recipe categories
            String[] categoryIds = req.getParameterValues("categories");
            if (categoryIds != null) {
                for (String cid : categoryIds) {
                    RecipeCategory rc = new RecipeCategory();
                    rc.setRecipeId(recipeId);
                    rc.setCategoryId(Integer.parseInt(cid));
                    recipeCategoryDAO.addRecipeCategory(rc);
                }
            }

            // Save recipe ingredients
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

            // Redirect to confirmation page
            resp.sendRedirect("recipeConfirmation?id=" + recipeId);

        } catch (Exception e) {
            throw new ServletException("Error creating recipe", e);
        }
    }
}
