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

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    private final UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || username.trim().isEmpty()) {
            req.setAttribute("error", "Username is required");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Password is required");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                req.setAttribute("error", "Username or password is incorrect");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
            if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                req.setAttribute("error", "Username or password is incorrect");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            resp.sendRedirect(req.getContextPath() + "/recipesList");

        } catch (SQLException e) {
            req.setAttribute("error", "Login failed");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}