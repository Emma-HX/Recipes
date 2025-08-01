<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.group6.recipes.model.Category" %>
<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Recipes</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Search Recipes</h2>
    <form action="search" method="get" class="row g-3">
        <div class="col-md-6">
            <input type="text" name="keyword" class="form-control" placeholder="Enter keyword..." />
        </div>
        <div class="col-md-4">
            <select name="category" class="form-select">
                <option value="">All Categories</option>
                <% if (categories != null) {
                    for (Category cat : categories) { %>
                <option value="<%=cat.getName()%>"><%=cat.getName()%></option>
                <%  }} %>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary w-100">Search</button>
        </div>
    </form>
</div>
</body>
</html>
