<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="css/style.css">
<nav class="navbar navbar-expand-lg dr-navbar">
    <div class="container">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/">
        </a>
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
