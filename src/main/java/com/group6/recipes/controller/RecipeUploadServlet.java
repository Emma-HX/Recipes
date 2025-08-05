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
        int userId = 1; // Ajustar según autenticación
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String prepSteps = req.getParameter("prepSteps");
        // 1. upload image
        Part imagePart = req.getPart("imageFile");
        String imagePath = null;
        String imagesDir = req.getServletContext().getRealPath("/images");
        File dir = new File(imagesDir);
        if (!dir.exists()) dir.mkdirs();

        if (imagePart != null && imagePart.getSize() > 0 && imagePart.getSubmittedFileName() != null && !imagePart.getSubmittedFileName().isEmpty()) {
            // generate image file name
            String submittedFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int dot = submittedFileName.lastIndexOf('.');
            if (dot >= 0) ext = submittedFileName.substring(dot);
            String newFileName = UUID.randomUUID() + ext;
            File imageFile = new File(dir, newFileName);

            // save file
            try (InputStream in = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(imageFile)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            }
            // save relative path
            imagePath = "/images/" + newFileName;
        }

        Recipe recipe = new Recipe();
        recipe.setUserId(userId);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepSteps(prepSteps);
        recipe.setImagePath(imagePath);  // Guardamos la URL directamente

        try {
            int recipeId = recipesDAO.addRecipe(recipe);
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

            resp.sendRedirect("recipeConfirmation?id=" + recipeId);

        } catch (Exception e) {
            throw new ServletException("Error creating recipe", e);
        }
    }
}
