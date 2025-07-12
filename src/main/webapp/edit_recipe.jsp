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
  </script>
</head>
<body>
<div class="container mt-4">
  <h2>Edit Recipe</h2>
  <form action="editRecipe" method="post">
    <input type="hidden" name="id" value="<%= ((Recipe)request.getAttribute("recipe")).getRecipeId() %>">
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
      <label class="form-label">Categories</label>
      <select class="form-select" name="categories" multiple>
        <%
          List<Category> categories = (List<Category>)request.getAttribute("categories");
          List<Integer> selectedCategories = (List<Integer>)request.getAttribute("selectedCategories");
          if (categories != null) {
            for (Category c : categories) {
        %>
        <option value="<%=c.getCategoryId()%>" <%= (selectedCategories != null && selectedCategories.contains(c.getCategoryId())) ? "selected" : "" %>>
          <%=c.getName()%>
        </option>
        <%
            }
          }
        %>
      </select>
      <div class="form-text">按住Ctrl可多选</div>
    </div>
    <div class="mb-3">
      <label class="form-label">Ingredients</label>
      <div id="ingredients-container">
        <%
          List<Ingredient> allIngredients = (List<Ingredient>)request.getAttribute("ingredients");
          List<Unit> units = (List<Unit>)request.getAttribute("units");
          List<RecipeIngredient> recipeIngredients = (List<RecipeIngredient>)request.getAttribute("recipeIngredients");
          if (recipeIngredients != null && !recipeIngredients.isEmpty()) {
            for (RecipeIngredient ri : recipeIngredients) {
        %>
        <div class="row mb-2 ingredient-row">
          <div class="col-md-4">
            <select class="form-select" name="ingredientIds">
              <%
                for (Ingredient ing : allIngredients) {
              %>
              <option value="<%=ing.getIngredientId()%>" <%= (ri.getIngredientId() == ing.getIngredientId()) ? "selected" : "" %>><%=ing.getName()%></option>
              <%
                }
              %>
            </select>
          </div>
          <div class="col-md-3">
            <input type="number" step="0.01" min="0" class="form-control" name="quantities" value="<%=ri.getQuantity()%>" required>
          </div>
          <div class="col-md-3">
            <select class="form-select" name="unitIds">
              <%
                for (Unit u : units) {
              %>
              <option value="<%=u.getUnitId()%>" <%= (ri.getUnitId() == u.getUnitId()) ? "selected" : "" %>><%=u.getUnitName()%></option>
              <%
                }
              %>
            </select>
          </div>
          <div class="col-md-2">
            <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">删除</button>
          </div>
        </div>
        <%
          }
        } else {
        %>
        <div class="row mb-2 ingredient-row">
          <div class="col-md-4">
            <select class="form-select" name="ingredientIds">
              <%
                for (Ingredient ing : allIngredients) {
              %>
              <option value="<%=ing.getIngredientId()%>"><%=ing.getName()%></option>
              <%
                }
              %>
            </select>
          </div>
          <div class="col-md-3">
            <input type="number" step="0.01" min="0" class="form-control" name="quantities" required>
          </div>
          <div class="col-md-3">
            <select class="form-select" name="unitIds">
              <%
                for (Unit u : units) {
              %>
              <option value="<%=u.getUnitId()%>"><%=u.getUnitName()%></option>
              <%
                }
              %>
            </select>
          </div>
          <div class="col-md-2">
            <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">删除</button>
          </div>
        </div>
        <%
          }
        %>
      </div>
      <button type="button" class="btn btn-secondary mt-2" onclick="addIngredientRow()">添加配料</button>
    </div>
    <button type="submit" class="btn btn-success">保存修改</button>
    <a href="recipes" class="btn btn-secondary">返回</a>
  </form>
</div>

<!-- 配料行模板 -->
<template id="ingredient-row-template">
  <div class="row mb-2 ingredient-row">
    <div class="col-md-4">
      <select class="form-select" name="ingredientIds">
        <% for (Ingredient ing : (List<Ingredient>)request.getAttribute("ingredients")) { %>
        <option value="<%=ing.getIngredientId()%>"><%=ing.getName()%></option>
        <% } %>
      </select>
    </div>
    <div class="col-md-3">
      <input type="number" step="0.01" min="0" class="form-control" name="quantities" required>
    </div>
    <div class="col-md-3">
      <select class="form-select" name="unitIds">
        <% for (Unit u : (List<Unit>)request.getAttribute("units")) { %>
        <option value="<%=u.getUnitId()%>"><%=u.getUnitName()%></option>
        <% } %>
      </select>
    </div>
    <div class="col-md-2">
      <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">删除</button>
    </div>
  </div>
</template>
</body>
</html>
