<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.group6.recipes.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Recipe</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #fdfdfd;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        h2 {
            color: #2c3e50;
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
        }
        form {
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.08);
        }
        label.form-label {
            font-weight: 600;
            color: #495057;
        }
        input.form-control:focus,
        textarea.form-control:focus,
        select.form-select:focus {
            border-color: #198754;
            box-shadow: 0 0 0 0.2rem rgba(25, 135, 84, 0.25);
        }
        button.btn-danger {
            padding: 4px 10px;
        }
        button.btn-success {
            padding: 8px 20px;
            font-size: 1rem;
        }
        .btn-secondary.mt-2 {
            background-color: #6c757d;
            border: none;
        }
        .alert-success {
            font-weight: 500;
            font-size: 1rem;
        }
        .alert-danger {
            font-weight: 500;
            font-size: 1rem;
        }
        .category-btn {
            margin: 2px 4px 2px 0;
            padding: 4px 12px;
            border-radius: 16px;
            border: 1px solid #ccc;
            background: #f8f9fa;
            cursor: pointer;
            transition: background-color 0.3s, border-color 0.3s;
        }
        .category-btn.selected {
            background: #d1e7dd;
            border-color: #198754;
            color: #198754;
            font-weight: bold;
        }
        .category-btn:hover {
            background-color: #e2f0eb;
            border-color: #157347;
        }
    </style>
    <script>
        function addIngredientRow() {
            const container = document.getElementById('ingredients-container');
            const template = document.getElementById('ingredient-row-template');
            const clone = template.content.cloneNode(true);
            container.appendChild(clone);
        }

        function removeIngredientRow(btn) {
            btn.closest('.ingredient-row').remove();
        }

        function toggleCategory(btn, id) {
            btn.classList.toggle('selected');
            const input = document.getElementById('cat_' + id);
            input.checked = !input.checked;
        }
    </script>
</head>
<body>
<div class="container mt-4">
    <h2>Create New Recipe</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger" role="alert">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <% if (request.getAttribute("successMessage") != null) { %>
    <div class="alert alert-success" role="alert">
        <%= request.getAttribute("successMessage") %>
    </div>
    <a href="create_recipe" class="btn btn-primary mb-3">Create New Recipe</a>
    <% } %>

    <form action="create_recipe" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label">Upload image</label>
            <input type="file" class="form-control" name="imageFile" accept="image/*">
        </div>

        <div class="mb-3">
            <label class="form-label">Recipe Name</label>
            <input type="text" class="form-control" name="title" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Description</label>
            <textarea class="form-control" name="description" rows="2" required></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">Preparation Steps</label>
            <textarea class="form-control" name="prepSteps" rows="4" required></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">Categories</label><br>
            <div>
                <%
                    List<Category> allCategories = (List<Category>)request.getAttribute("allCategories");
                    if (allCategories != null) {
                        for (Category c : allCategories) {
                %>
                <button type="button" class="category-btn" onclick="toggleCategory(this, <%=c.getCategoryId()%>)"><%=c.getName()%></button>
                <input type="checkbox" id="cat_<%=c.getCategoryId()%>" name="categories" value="<%=c.getCategoryId()%>" style="display:none;">
                <%
                        }
                    }
                %>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Ingredients</label>
            <div id="ingredients-container">
                <div class="row mb-2 ingredient-row">
                    <div class="col-md-5">
                        <input type="text" class="form-control" name="ingredientNames" required placeholder="Ingredient Name">
                    </div>
                    <div class="col-md-3">
                        <input type="number" step="0.01" min="0" class="form-control" name="quantities" required placeholder="Quantity">
                    </div>
                    <div class="col-md-3">
                        <select class="form-select" name="unitIds">
                            <%
                                List<Unit> allUnits = (List<Unit>)request.getAttribute("allUnits");
                                if (allUnits != null) {
                                    for (Unit u : allUnits) {
                            %>
                            <option value="<%=u.getUnitId()%>"><%=u.getUnitName()%></option>
                            <%
                                }
                            } else {
                            %>
                            <option disabled>No units available</option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">Delete</button>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-secondary mt-2" onclick="addIngredientRow()">Add Ingredient</button>
        </div>

        <button type="submit" class="btn btn-success">Create</button>
        <a href="recipesList" class="btn btn-secondary">Cancel</a>
    </form>
</div>

<!-- Ingredients Template -->
<template id="ingredient-row-template">
    <div class="row mb-2 ingredient-row">
        <div class="col-md-5">
            <input type="text" class="form-control" name="ingredientNames" required placeholder="Ingredient Name">
        </div>
        <div class="col-md-3">
            <input type="number" step="0.01" min="0" class="form-control" name="quantities" required placeholder="Quantity">
        </div>
        <div class="col-md-3">
            <select class="form-select" name="unitIds">
                <% for (Unit u : (List<Unit>)request.getAttribute("allUnits")) { %>
                <option value="<%=u.getUnitId()%>"><%=u.getUnitName()%></option>
                <% } %>
            </select>
        </div>
        <div class="col-md-1">
            <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)">Delete</button>
        </div>
    </div>
</template>
</body>
</html>
