<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.group6.recipes.model.Recipe" %>
<%@ page import="com.group6.recipes.model.RecipeIngredient" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recipe Confirmation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .recipe-img {
            max-width: 300px;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
            background: #f0f0f0;
        }
        .ingredients-box, .steps-box {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-top: 20px;
            min-height: 180px;
        }
    </style>
</head>
<body>
<jsp:include page="header_template.jsp" />
<div class="container mt-5">
    <%
        Recipe recipe = (Recipe) request.getAttribute("recipe");
        List<RecipeIngredient> ingredients = (List<RecipeIngredient>) request.getAttribute("ingredientsList");
        String[] steps = recipe.getPrepSteps() != null ? recipe.getPrepSteps().split("\\r?\\n") : new String[0];
    %>

    <h1 class="mb-4">Recipe Created Successfully!</h1>

    <div class="row">
        <div class="col-md-4 text-center">
            <img src="<%= (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) ? request.getContextPath() + recipe.getImagePath() : "https://via.placeholder.com/300x200?text=No+Image" %>"
                 alt="Recipe Image" class="recipe-img mb-3">
            <div class="ingredients-box">
                <h5>Ingredients</h5>
                <ul class="list-unstyled">
                    <% if (ingredients != null && !ingredients.isEmpty()) {
                        for (RecipeIngredient ing : ingredients) { %>
                    <li>
                        <%= ing.getIngredientName() %> — <%= ing.getQuantity() %> <%= ing.getUnitName() %>
                    </li>
                    <%   }
                    } else { %>
                    <li>No ingredients available</li>
                    <% } %>
                </ul>
            </div>
        </div>

        <div class="col-md-8">
            <h2><%= recipe.getTitle() %></h2>
            <p class="text-muted"><%= recipe.getDescription() != null ? recipe.getDescription() : "" %></p>
            <div class="steps-box">
                <h5>Preparation Steps</h5>
                <ol>
                    <% for(String step : steps) {
                        if(step.trim().length() > 0) { %>
                    <li><%= step.trim() %></li>
                    <% } } %>
                </ol>
            </div>
            <div class="mt-4">
                <a href="editRecipe?id=<%= recipe.getRecipeId() %>" class="btn btn-warning me-2">Edit</a>
                <form action="deleteRecipe" method="post" style="display:inline;"
                      onsubmit="return confirm('Are you sure you want to delete this recipe?');">
                    <input type="hidden" name="id" value="<%= recipe.getRecipeId() %>"/>
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>

    <a href="recipesList" class="btn btn-secondary mt-4">Return to List</a>
</div>
</body>
</html>
