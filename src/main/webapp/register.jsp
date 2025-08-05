<%--
  Created by IntelliJ IDEA.
  User: emma_
  Date: 8/5/2025
  Time: 1:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>User Registration</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    body {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 20px 0;
    }
    .register-container {
      background: white;
      border-radius: 15px;
      box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
      padding: 40px;
      width: 100%;
      max-width: 450px;
    }
    .register-header {
      text-align: center;
      margin-bottom: 30px;
    }
    .register-header h2 {
      color: #333;
      font-weight: 600;
    }
    .form-control {
      border-radius: 10px;
      border: 2px solid #e9ecef;
      padding: 12px 15px;
      transition: all 0.3s ease;
    }
    .form-control:focus {
      border-color: #667eea;
      box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
    }
    .btn-register {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      border-radius: 10px;
      padding: 12px;
      font-weight: 600;
      width: 100%;
      margin-top: 20px;
    }
    .btn-register:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
    }
    .login-link {
      text-align: center;
      margin-top: 20px;
    }
    .alert {
      border-radius: 10px;
      margin-bottom: 20px;
    }
    .password-requirements {
      font-size: 0.875rem;
      color: #6c757d;
      margin-top: 5px;
    }
  </style>
</head>
<body>
<div class="register-container">
  <div class="register-header">
    <h2>Create Account</h2>
    <p class="text-muted">Please fill in the following information to complete your registration</p>
  </div>

  <% if (request.getAttribute("error") != null) { %>
  <div class="alert alert-danger" role="alert">
    <%= request.getAttribute("error") %>
  </div>
  <% } %>

  <form action="register" method="post" id="registerForm">
    <div class="mb-3">
      <label for="username" class="form-label">Username</label>
      <input type="text" class="form-control" id="username" name="username" required>
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" class="form-control" id="email" name="email" required>
    </div>
    <div class="mb-3">
      <label for="password" class="form-label">Password</label>
      <input type="password" class="form-control" id="password" name="password" required>
      <div class="password-requirements">The password must be at least 6 characters</div>
    </div>
    <div class="mb-3">
      <label for="confirmPassword" class="form-label">Confirm Password</label>
      <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
    </div>
    <button type="submit" class="btn btn-primary btn-register">Register</button>
  </form>

  <div class="login-link">
    <p class="mb-0">Already has an account? <a href="login" class="text-decoration-none">Log in</a></p>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  document.getElementById('registerForm').addEventListener('submit', function(e) {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
      e.preventDefault();
      alert('The passwords do not match');
      return false;
    }

    if (password.length < 6) {
      e.preventDefault();
      alert('The password must be at least 6 characters');
      return false;
    }
  });
</script>
</body>
</html>
