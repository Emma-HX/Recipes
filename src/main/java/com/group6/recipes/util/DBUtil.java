package com.group6.recipes.util;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DBUtil {
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("jdbc.url");
            username = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");
            Class.forName(prop.getProperty("com.mysql.cj.jdbc.Driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}