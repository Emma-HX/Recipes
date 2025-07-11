package com.group6.recipes.model;

import java.util.Date;

public class Recipe {
    private int recipeId;
    private int userId;
    private String title;
    private String description;
    private String prepSteps;
    private Date createdAt;
    private Date updatedAt;


    public int getRecipeId() {
        return recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrepSteps() {
        return prepSteps;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrepSteps(String prepSteps) {
        this.prepSteps = prepSteps;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}