<%--
  Created by IntelliJ IDEA.
  User: emma_
  Date: 8/5/2025
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Delicious Recipes - Home</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    body {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
    }
    .hero-section {
      background: rgba(255, 255, 255, 0.95);
      border-radius: 20px;
      padding: 60px 40px;
      margin: 40px 0;
      box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
    }
    .feature-card {
      background: white;
      border-radius: 15px;
      padding: 30px;
      margin: 20px 0;
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease;
    }
    .feature-card:hover {
      transform: translateY(-5px);
    }
    .btn-custom {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      border-radius: 10px;
      padding: 12px 30px;
      font-weight: 600;
      color: white;
      text-decoration: none;
      display: inline-block;
      transition: all 0.3s ease;
    }
    .btn-custom:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
      color: white;
    }
    .welcome-text {
      font-size: 1.2rem;
      color: #6c757d;
      margin-bottom: 30px;
    }
  </style>
</head>
<body>
<!-- Simple Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container">
    <div class="navbar-nav ms-auto">
      <% if (session.getAttribute("user") != null) { %>
      <span class="navbar-text me-3">
                          Welcome, <%= session.getAttribute("username") %>
                      </span>
      <a class="nav-link" href="<%= request.getContextPath() %>/logout">Logout</a>
      <% } else { %>
      <a class="nav-link" href="<%= request.getContextPath() %>/login">Login</a>
      <a class="nav-link" href="<%= request.getContextPath() %>/register">Register</a>
      <% } %>
    </div>
  </div>
</nav>

<div class="container">
  <div class="hero-section text-center">
    <h1 class="display-4 mb-4">Welcome to Delicious Recipes</h1>
    <p class="welcome-text">
      <% if (session.getAttribute("user") != null) { %>
      Welcome back, <strong><%= session.getAttribute("username") %></strong>! Start exploring the world of delicious recipes.
      <% } else { %>
      Discover, share, and save your favorite recipes. Join our food-loving community!
      <% } %>
    </p>

    <div class="row mt-5">
      <div class="col-md-4">
        <div class="feature-card text-center">
          <h3>Browse Recipes</h3>
          <p class="text-muted">Explore our rich collection of recipes and find dishes you love.</p>
          <a href="<%= request.getContextPath() %>/recipesList" class="btn-custom">Browse Recipes</a>
        </div>
      </div>

      <div class="col-md-4">
        <div class="feature-card text-center">
          <h3>Search Recipes</h3>
          <p class="text-muted">Quickly find the recipes you need by keywords, categories, or ingredients.</p>
          <a href="<%= request.getContextPath() %>/search.jsp" class="btn-custom">Search Recipes</a>
        </div>
      </div>

      <div class="col-md-4">
        <div class="feature-card text-center">
          <h3>Share Recipes</h3>
          <p class="text-muted">
            <% if (session.getAttribute("user") != null) { %>
            Share your delicious recipes and let others enjoy your creations.
            <% } else { %>
            Log in to start sharing your amazing recipes.
            <% } %>
          </p>
          <% if (session.getAttribute("user") != null) { %>
          <a href="<%= request.getContextPath() %>/create_recipe" class="btn-custom">Upload Recipe</a>
          <% } else { %>
          <a href="<%= request.getContextPath() %>/login" class="btn-custom">Log In</a>
          <% } %>
        </div>
      </div>
    </div>

    <% if (session.getAttribute("user") == null) { %>
    <div class="mt-5">
      <p class="mb-3">Don't have an account yet? Join us now!</p>
      <a href="<%= request.getContextPath() %>/register" class="btn-custom me-3">Register</a>
      <a href="<%= request.getContextPath() %>/login" class="btn-custom">Log In</a>
    </div>
    <% } %>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
