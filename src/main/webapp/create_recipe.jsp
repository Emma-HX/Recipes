<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.group6.recipes.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create Recipe</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        body {
            background-color: #fafafa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        h2 {
            color: #2c3e50;
            font-weight: 700;
            text-align: center;
            margin-bottom: 40px;
        }
        form {
            background: white;
            padding: 30px 35px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgb(0 0 0 / 0.1);
            max-width: 800px;
            margin: 0 auto 50px auto;
        }
        label.form-label {
            font-weight: 600;
            color: #444;
        }
        .category-btn {
            margin: 3px 6px 3px 0;
            padding: 6px 16px;
            border-radius: 20px;
            border: 1.8px solid #ccc;
            background: #f5f7f9;
            cursor: pointer;
            transition: all 0.25s ease-in-out;
            user-select: none;
        }
        .category-btn.selected {
            background: #198754;
            border-color: #198754;
            color: white;
            font-weight: 700;
        }
        .category-btn:hover {
            background-color: #e1f0df;
            border-color: #157347;
            color: #157347;
        }
        .ingredient-row > div {
            margin-bottom: 12px;
        }
        button.btn-danger {
            padding: 6px 14px;
        }
        button.btn-success {
            font-size: 1.1rem;
            padding: 10px 26px;
        }
        .btn-secondary {
            font-size: 1rem;
        }
        .alert {
            max-width: 800px;
            margin: 15px auto;
            font-weight: 600;
            font-size: 1.1rem;
        }
        h2 {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            font-weight: 700;
            font-size: 2.25rem;
            color: #000000;
            text-align: center;
            margin-bottom: 40px;
            letter-spacing: 1.5px;
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
            if (document.querySelectorAll('.ingredient-row').length > 1) {
                btn.closest('.ingredient-row').remove();
            } else {
                alert('At least one ingredient is required.');
            }
        }

        function toggleCategory(btn, id) {
            btn.classList.toggle('selected');
            const input = document.getElementById('cat_' + id);
            input.checked = !input.checked;
        }
    </script>
</head>
<body>
<jsp:include page="header_template.jsp" />
<div class="container">
    <h2>Create New Recipe</h2>
    <div class="text-center mb-4">
    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger text-center" role="alert">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <% if (request.getAttribute("successMessage") != null) { %>
    <div class="alert alert-success text-center" role="alert">
        <%= request.getAttribute("successMessage") %>
    </div>
    <div class="text-center mb-4">
        <a href="create_recipe" class="btn btn-primary">Create Another Recipe</a>
    </div>
    <% } %>

    <form action="create_recipe" method="post" enctype="multipart/form-data" novalidate>
        <div class="mb-4">
            <label for="imageFile" class="form-label">Upload Image</label>
            <input type="file" id="imageFile" name="imageFile" class="form-control" accept="image/*" />
        </div>

        <div class="mb-4">
            <label for="title" class="form-label">Recipe Name</label>
            <input type="text" id="title" name="title" class="form-control" placeholder="Enter recipe title" required />
        </div>

        <div class="mb-4">
            <label for="description" class="form-label">Description</label>
            <textarea id="description" name="description" rows="2" class="form-control" placeholder="Short description" required></textarea>
        </div>

        <div class="mb-4">
            <label for="prepSteps" class="form-label">Preparation Steps</label>
            <textarea id="prepSteps" name="prepSteps" rows="5" class="form-control" placeholder="List preparation steps here" required></textarea>
        </div>

        <div class="mb-4">
            <label class="form-label">Categories</label><br/>
            <div>
                <%
                    List<Category> allCategories = (List<Category>)request.getAttribute("allCategories");
                    if (allCategories != null) {
                        for (Category c : allCategories) {
                %>
                <button type="button" class="category-btn" onclick="toggleCategory(this, <%=c.getCategoryId()%>)"><%=c.getName()%></button>
                <input type="checkbox" id="cat_<%=c.getCategoryId()%>" name="categories" value="<%=c.getCategoryId()%>" style="display:none;" />
                <%
                        }
                    }
                %>
            </div>
        </div>

        <div class="mb-4">
            <label class="form-label">Ingredients</label>
            <div id="ingredients-container">
                <div class="row ingredient-row align-items-center g-2">
                    <div class="col-md-5">
                        <input type="text" name="ingredientNames" class="form-control" placeholder="Ingredient Name" required />
                    </div>
                    <div class="col-md-3">
                        <input type="number" name="quantities" step="0.01" min="0" class="form-control" placeholder="Quantity" required />
                    </div>
                    <div class="col-md-3">
                        <select name="unitIds" class="form-select" required>
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
                    <div class="col-md-1 d-grid">
                        <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)" title="Remove ingredient">&times;</button>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-outline-secondary mt-3" onclick="addIngredientRow()">+ Add Ingredient</button>
        </div>

        <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-success">Create Recipe</button>
            <a href="recipesList" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>

<!-- Ingredient row template for JS cloning -->
<template id="ingredient-row-template">
    <div class="row ingredient-row align-items-center g-2 mb-2">
        <div class="col-md-5">
            <input type="text" name="ingredientNames" class="form-control" placeholder="Ingredient Name" required />
        </div>
        <div class="col-md-3">
            <input type="number" name="quantities" step="0.01" min="0" class="form-control" placeholder="Quantity" required />
        </div>
        <div class="col-md-3">
            <select name="unitIds" class="form-select" required>
                <% for (Unit u : (List<Unit>)request.getAttribute("allUnits")) { %>
                <option value="<%=u.getUnitId()%>"><%=u.getUnitName()%></option>
                <% } %>
            </select>
        </div>
        <div class="col-md-1 d-grid">
            <button type="button" class="btn btn-danger" onclick="removeIngredientRow(this)" title="Remove ingredient">&times;</button>
        </div>
    </div>
</template>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
