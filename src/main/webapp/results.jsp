<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.group6.recipes.model.Recipe" %>
<%
    List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="header_template.jsp" />
<div class="container mt-4">
    <h2>Search Results</h2>
    <a href="search.jsp" class="btn btn-secondary mb-3">Back to Search</a>

    <% if (recipes == null || recipes.isEmpty()) { %>
    <div class="alert alert-warning">No recipes found.</div>
    <% } else { %>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <% for (Recipe r : recipes) { %>
        <div class="col">
            <div class="card h-100">
                <img src="<%= (r.getImagePath() != null && !r.getImagePath().isEmpty()) ? request.getContextPath() + r.getImagePath() : "https://via.placeholder.com/300x180?text=No+Image" %>"
                     class="card-img-top" style="height:180px; object-fit:cover;">
                <div class="card-body">
                    <h5 class="card-title"><%= r.getTitle() %></h5>
                    <p class="card-text"><%= r.getDescription() %></p>
                    <a href="viewRecipe?id=<%=r.getRecipeId()%>" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>
</div>
</body>
</html>
