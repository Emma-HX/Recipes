package com.group6.recipes.dao;

import com.group6.recipes.model.User;
import java.util.List;

public interface UserDAO {
    public void addUser(User user) ;
    public User getUserById(int userId);
    public User getUserByUsername(String username) ;
    public List<User> getAllUsers();
    public void updateUser(User user) ;
    public void deleteUser(int userId) ;
} 