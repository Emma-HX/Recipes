package com.group6.recipes.controller;

import com.group6.recipes.dao.UserDAOImpl;
import com.group6.recipes.model.User;
import com.group6.recipes.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
    private final UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");

        if (username == null || username.trim().isEmpty()) {
            req.setAttribute("error", "User name is required");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Password is required");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "The passwords do not match");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (password.length() < 6) {
            req.setAttribute("error", "The password must be at least 6 characters");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        try {
            User existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                req.setAttribute("error", "The username is already taken");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPasswordHash(PasswordUtil.hashPassword(password));
            newUser.setEmail(email);

            userDAO.addUser(newUser);

            req.setAttribute("success", "Registration successful");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", "Registration failed");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}