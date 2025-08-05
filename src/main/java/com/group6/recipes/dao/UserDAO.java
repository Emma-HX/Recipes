package com.group6.recipes.dao;

import com.group6.recipes.model.User;
import java.util.List;
import java.sql.SQLException;

public interface UserDAO {
    public void addUser(User user) throws SQLException;
    public User getUserById(int userId) throws SQLException;
    public User getUserByUsername(String username) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public void updateUser(User user) throws SQLException;
    public void deleteUser(int userId) throws SQLException;
}