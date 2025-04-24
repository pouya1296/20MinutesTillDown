package org.example.controller;

import org.example.Model.User;

import java.sql.*;

public class DatabaseManager {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:users.db");
    }

    public static User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("avatarPath")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, question, answer, avatarPath) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.connect();
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        prepareStatement.setString(1, user.username);
        prepareStatement.setString(2, user.password);
        prepareStatement.setString(3, user.question);
        prepareStatement.setString(4, user.answer);
        prepareStatement.setString(5, user.avatarPath);
        prepareStatement.executeUpdate();
    }
}
