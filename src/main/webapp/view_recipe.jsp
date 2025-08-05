<%--
  Created by IntelliJ IDEA.
  User: Xue Han
  Date: 7/11/2025
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.group6.recipes.model.Recipe" %>
<%@ page import="com.group6.recipes.model.RecipeIngredient" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>View Recipe - Recipe Management</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    .recipe-img {
      width: 100%;
      max-width: 250px;
      height: 180px;
      object-fit: cover;
      border-radius: 8px;
      background: #f0f0f0;
    }
    .ingredients-box, .steps-box {
      background: #f8f9fa;
      border-radius: 8px;
      padding: 20px;
      min-height: 200px;
    }
    .ingredients-box {
      margin-top: 20px;
    }
    .steps-box {
      margin-top: 20px;
    }
  </style>
</head>
<body>
<jsp:include page="header_template.jsp" />
<div class="container mt-4">
  <%
    Recipe recipe = (Recipe)request.getAttribute("recipe");
    List<RecipeIngredient> ingredients = (List<RecipeIngredient>) request.getAttribute("ingredientsList");
    String[] steps = recipe.getPrepSteps() != null ? recipe.getPrepSteps().split("\n|\r\n") : new String[0];
  %>
  <div class="row">
    <div class="col-md-4">
      <img src="<%= (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) ? request.getContextPath() + recipe.getImagePath() : "https://via.placeholder.com/250x180?text=No+Image" %>"
           alt="Recipe Image" class="recipe-img mb-3">
      <div class="ingredients-box">
        <h5>Ingredients:</h5>
        <ul class="list-unstyled">
          <% for(RecipeIngredient ing : ingredients) { if(ing != null) { %>
          <li><%= ing.getIngredientName() %>   <%= ing.getQuantity() %>   <%= ing.getUnitName()%></li>
          <% }} %>
        </ul>
      </div>
    </div>
    <div class="col-md-8">
      <h2><%= recipe.getTitle() %></h2>
      <p class="mb-4 text-muted"><%= recipe.getDescription() %></p>
      <div class="steps-box">
        <h5>Steps:</h5>
        <ol>
          <% for(String step : steps) { if(step.trim().length() > 0) { %>
          <li><%= step.trim() %></li>
          <% }} %>
        </ol>
      </div>
      <div class="mt-4 d-flex gap-2">
        <a href="editRecipe?id=<%=recipe.getRecipeId()%>" class="btn btn-warning">Edit</a>
        <form action="deleteRecipe" method="post" onsubmit="return confirm('Confirm to delete this recipe？');">
          <input type="hidden" name="id" value="<%=recipe.getRecipeId()%>" />
          <button type="submit" class="btn btn-danger">Delete</button>
        </form>
      </div>
    </div>
  </div>
  <a href="recipesList" class="btn btn-secondary mt-4">Return to List</a>
</div>
</body>
</html>

