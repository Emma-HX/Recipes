// src/main/java/com/group6/recipes/controller/RecipeEditServlet.java
package com.group6.recipes.controller;

import com.group6.recipes.dao.*;
import com.group6.recipes.model.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet("/editRecipe")
public class RecipeEditServlet extends HttpServlet {
    private final RecipesDAOImpl recipesDAO = new RecipesDAOImpl();
    private final UnitDAOImpl unitDAO = new UnitDAOImpl();
    private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    private final RecipeIngredientDAOImpl recipeIngredientDAO = new RecipeIngredientDAOImpl();
    private final RecipeCategoryDAOImpl recipeCategoryDAO = new RecipeCategoryDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recipeId = Integer.parseInt(req.getParameter("id"));
        try {
            Recipe recipe = recipesDAO.getRecipeById(recipeId);
            List<Unit> allUnits = unitDAO.getAllUnits();
            List<Category> allCategories = categoryDAO.getAllCategories();
            List<RecipeCategory> recipeCategoryList = recipeCategoryDAO.getCategoriesByRecipe(recipeId);
            List<RecipeIngredient> ingredients = recipeIngredientDAO.getIngredientsByRecipe(recipeId);

            List<Integer> selectedCategories = recipeCategoryList.stream()
                    .map(RecipeCategory::getCategoryId)
                    .collect(Collectors.toList());

            req.setAttribute("allCategories", allCategories);
            req.setAttribute("selectedCategories", selectedCategories);

            req.setAttribute("recipe", recipe);
            req.setAttribute("allUnits", allUnits);
            req.setAttribute("allCategories", allCategories);
            req.setAttribute("selectedCategories", selectedCategories);
            req.setAttribute("recipeIngredients", ingredients);
            req.getRequestDispatcher("/edit_recipe.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Integer userId = (Integer) req.getSession().getAttribute("userId");
//        if (userId == null) {
//            resp.sendRedirect("login.jsp");
//            return;
//        }
        int recipeId = Integer.parseInt(getField(req, "id"));
        String title = getField(req, "title");
        String description = getField(req, "description");
        String prepSteps = getField(req, "prepSteps");

        // 1. upload image
        Part imagePart = req.getPart("imageFile");
        String imagePath = null;
        String imagesDir = req.getServletContext().getRealPath("/images");
        File dir = new File(imagesDir);
        if (!dir.exists()) dir.mkdirs();

        // get the old image path
        Recipe oldRecipe = null;
        try {
            oldRecipe = recipesDAO.getRecipeById(recipeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String oldImagePath = (oldRecipe != null) ? oldRecipe.getImagePath() : null;

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
        } else {
            imagePath = oldImagePath;
        }
        // 2. Update Recipe
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setUserId(oldRecipe.getUserId());
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepSteps(prepSteps);
        recipe.setImagePath(imagePath);

        try {
            recipesDAO.updateRecipe(recipe);
            // get new categories info
            String[] categoryIds = req.getParameterValues("categories");
            if (categoryIds != null) {
                updateRecipeCategory(recipeId, categoryIds);
            }
            // get new ingredients info
            String[] ingredientNames = req.getParameterValues("ingredientNames");
            String[] quantities = req.getParameterValues("quantities");
            String[] unitIds = req.getParameterValues("unitIds");

            List<RecipeIngredient> ingredients = new ArrayList<>();
            if (ingredientNames != null && quantities != null && unitIds != null) {
                for (int i = 0; i < ingredientNames.length; i++) {
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setIngredientName(ingredientNames[i]);
                    ri.setQuantity(Double.parseDouble(quantities[i]));
                    ri.setUnitId(Integer.parseInt(unitIds[i]));
                    ri.setRecipeId(recipeId);
                    ingredients.add(ri);
                }
            }
            updateRecipeIngredient(recipeId, ingredients);

            resp.sendRedirect("recipesList");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    private String getField(HttpServletRequest req, String fieldName) throws IOException, ServletException {
        Part part = req.getPart(fieldName);
        if (part == null) return null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }
    private void updateRecipeCategory(int recipeId, String[] categoryIds) throws SQLException {
        recipeCategoryDAO.deleteByRecipe(recipeId);
        for (String categoryIdStr : categoryIds) {
            int categoryId = Integer.parseInt(categoryIdStr);
            RecipeCategory rc = new RecipeCategory();
            rc.setCategoryId(categoryId);
            rc.setRecipeId(recipeId);
            recipeCategoryDAO.addRecipeCategory(rc);
        }
    }

    private void updateRecipeIngredient(int recipeId, List<RecipeIngredient> ingredients) throws SQLException {
        recipeIngredientDAO.deleteByRecipe(recipeId);
        for (RecipeIngredient ri : ingredients) {
            recipeIngredientDAO.addRecipeIngredient(ri);
        }
    }
}