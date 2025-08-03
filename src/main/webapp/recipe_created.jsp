<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 8/2/2025
  Time: 11:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recipe Created</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5 text-center">
    <h2>${successMessage}</h2>
    <a href="create_recipe" class="btn btn-primary mt-3">Create New Recipe</a>
    <a href="recipesList" class="btn btn-secondary mt-3 ms-2">View Recipes</a>
</div>
</body>
</html>
