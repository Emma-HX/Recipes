<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="dr-login-body">
<div class="dr-login-container">
    <div class="dr-login-header">
        <h2>Welcome back</h2>
        <p class="text-muted">Please Login</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger dr-alert" role="alert">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <% if (request.getAttribute("success") != null) { %>
    <div class="alert alert-success dr-alert" role="alert">
        <%= request.getAttribute("success") %>
    </div>
    <% } %>

    <form action="login" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control dr-form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control dr-form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn dr-btn-login">Login</button>
    </form>

    <div class="dr-register-link">
        <p class="mb-0">Don't have an account?
            <a href="register" class="text-decoration-none">User Register</a>
        </p>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
