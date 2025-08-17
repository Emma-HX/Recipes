<%--
  Created by IntelliJ IDEA.
  User: Xue Han
  Date: 7/11/2025
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.group6.recipes.model.Recipe" %>
<%@ page import="com.group6.recipes.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recipes List - Recipe Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<jsp:include page="header_template.jsp" />
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <div class="mb-3">
                <a href="create_recipe" class="btn btn-outline-primary w-100">Create a Recipe</a>
            </div>
            <div class="sidebar">
                <h5>Recipe Search</h5>
                <form action="<%= request.getContextPath() %>/search" method="get" class="mb-3 d-flex">
                    <input type="text" class="form-control me-2" name="keyword" placeholder="Search...">
                    <button type="submit" class="btn btn-secondary">Search</button>
                </form>
                <h6>Categories</h6>
                <div class="category-list">
                    <%
                        List<Category> allCategories = (List<Category>) request.getAttribute("categories");
                        if (allCategories != null) {
                            for (Category c : allCategories) {
                    %>
                    <a href="recipesList?categoryId=<%=c.getCategoryId()%>" class="d-block mb-1"><%= c.getName() %></a>
                    <%
                            }
                        }
                    %>
                    <a href="recipesList" class="d-block mb-1">All Recipes</a>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <h3 class="mb-4">Recipe List</h3>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <%
                    List<Recipe> recipes = (List<Recipe>)request.getAttribute("recipes");
                    if (recipes != null && !recipes.isEmpty()) {
                        for (Recipe r : recipes) {
                %>
                <div class="col">
                    <a href="viewRecipe?id=<%=r.getRecipeId()%>" style="text-decoration:none; color:inherit;">
                        <div class="card h-100 shadow-sm">
                            <img src="<%= (r.getImagePath() != null && !r.getImagePath().isEmpty()) ? request.getContextPath() + r.getImagePath() : "https://via.placeholder.com/400x250?text=No+Image" %>"
                                 class="card-img-top" alt="Recipe Image" style="height:220px; object-fit:cover;">
                            <div class="card-body">
                                <h5 class="card-title text-center"><%=r.getTitle()%></h5>
                            </div>
                        </div>
                    </a>
                </div>
                <%
                    }
                } else {
                %>
                <div class="col-12">
                    <div class="alert alert-info">No recipes found.</div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
</body>
</html>