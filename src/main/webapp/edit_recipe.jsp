<%--
  Created by IntelliJ IDEA.
  User: Xue Han
  Date: 7/11/2025
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.group6.recipes.model.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Recipe</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    .category-btn {
      margin: 2px 4px 2px 0;
      padding: 4px 12px;
      border-radius: 16px;
      border: 1px solid #ccc;
      background: #f8f9fa;
      cursor: pointer;
    }
    .category-btn.selected {
      background: #d1e7dd;
      border-color: #198754;
      color: #198754;
      font-weight: bold;
    }
  </style>
  <script>
    function addIngredientRow() {
      const container = document.getElementById('ingredients-container');
      const template = document.getElementById('ingredient-row-template');
      const clone = template.content.cloneNode(true);
      container.appendChild(clone);
    }
    function removeIngredientRow(btn) {
      btn.closest('.ingredient-row').remove();
    }
    function toggleCategory(btn, id) {
      btn.classList.toggle('selected');
      const input = document.getElementById('cat_' + id);
      input.checked = !input.checked;
    }
  </script>
</head>
<body>
<jsp:include page="header_template.jsp" />
<div class="container mt-4">
  <h2>Edit Recipe</h2>
  <form action="editRecipe" method="post" enctype="multipart/form-data">
    <% Recipe recipe = (Recipe)request.getAttribute("recipe"); %>
    <input type="hidden" name="id" value="<%= recipe.getRecipeId() %>">
    <div class="mb-3 text-center">
      <img src="<%= (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) ? request.getContextPath() + recipe.getImagePath() : "https://via.placeholder.com/250x180?text=No+Image" %>"
           alt="Recipe Image" class="recipe-img mb-3" style="max-width:250px; max-height:180px; object-fit:cover; border-radius:8px;">
    </div>
    <div class="mb-3">
      <label class="form-label">Upload image</label>
      <input type="file" class="form-control" name="imageFile" accept="image/*">
    </div>

    <div class="mb-3">
      <label class="form-label">Recipe Name</label>
      <input type="text" class="form-control" name="title" value="<%= ((Recipe)request.getAttribute("recipe")).getTitle() %>" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Description</label>
      <textarea class="form-control" name="description" rows="2"><%= ((Recipe)request.getAttribute("recipe")).getDescription() %></textarea>
    </div>
    <div class="mb-3">
      <label class="form-label">Prepare Steps</label>
      <textarea class="form-control" name="prepSteps" rows="4"><%= ((Recipe)request.getAttribute("recipe")).getPrepSteps() %></textarea>
    </div>
    <div class="mb-3">
      <label class="form-label">Categories</label><br>
      <div>
        <%
          List<Category> allCategories = (List<Category>)request.getAttribute("allCategories");
          List<Integer> selectedCategories = (List<Integer>)request.getAttribute("selectedCategories");
          if (allCategories != null) {
            for (Category c : allCategories) {
              boolean isSelected = selectedCategories != null && selectedCategories.contains(c.getCategoryId());
        %>
        <button type="button" class="category-btn <%=isSelected ? "selected" : ""%>" onclick="toggleCategory(this, <%=c.getCategoryId()%>)"><%=c.getName()%></button>
        <input type="checkbox" id="cat_<%=c.getCategoryId()%>" name="categories" value="<%=c.getCategoryId()%>" style="display:none;" <%=isSelected ? "checked" : ""%>>
        <%      }
        }
        %>
      </div>
    </div>
    <div class="mb-3">
      <label class="form-label">Ingredients</label>
      <div id="ingredients-container">
        <%
          List<RecipeIngredient> recipeIngredients = (List<RecipeIngredient>)request.getAttribute("recipeIngredients");
          List<Unit> allUnits = (List<Unit>)request.getAttribute("allUnits");
          if (recipeIngredients != null && !recipeIngredients.isEmpty()) {
            for (RecipeIngredient ri : recipeIngredients) {
        %>
        <div class="row mb-2 ingredient-row">
          <div class="col-md-5">
            <input type="text" class="form-control" name="ingredientNames" value="<%=ri.getIngredientName() != null ? ri.getIngredientName() : ""%>" required placeholder="Ingredient Name">
          </div>
          <div class="col-md-3">
            <input type="number" step="0.01" min="0" class="form-control" name="quantities" value="<%=ri.getQuantity()%>" required placeholder="Quantity">
          </div>
          <div class="col-md-3">
            <select class="form-select" name="unitIds">
              <% for (Unit u : allUnits) { %>
              <option value="<%=u.getUnitId()%>" <%= (ri.getUnitId() == u.getUnitId()) ? "selected" : "" %>><%=u.getUnitName()%></option>
              <% } %>
            </select>
          </div>
          <div class="col-md-1">
            <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">Delete</button>
          </div>
        </div>
        <%      }
        }
        %>
      </div>
      <button type="button" class="btn btn-secondary mt-2" onclick="addIngredientRow()">Add Ingredient</button>
    </div>
    <button type="submit" class="btn btn-success">Save</button>
    <a href="recipesList" class="btn btn-secondary">Return</a>
  </form>
</div>

<!-- Ingredients Template -->
<template id="ingredient-row-template">
  <div class="row mb-2 ingredient-row">
    <div class="col-md-5">
      <input type="text" class="form-control" name="ingredientNames" required placeholder="Ingredient Name">
    </div>
    <div class="col-md-3">
      <input type="number" step="0.01" min="0" class="form-control" name="quantities" required placeholder="Quantity">
    </div>
    <div class="col-md-3">
      <select class="form-select" name="unitIds">
        <% for (Unit u : (List<Unit>)request.getAttribute("allUnits")) { %>
        <option value="<%=u.getUnitId()%>"><%=u.getUnitName()%></option>
        <% } %>
      </select>
    </div>
    <div class="col-md-1">
      <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">Delete</button>
    </div>
  </div>
</template>
</body>
</html>
