package org.example.controller;

import org.example.Model.User;

import java.sql.*;

public class DatabaseManager {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection( "jdbc:sqlite:C:/Users/ASUS/users.db");
    }

    public static User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        Connection con = DatabaseManager.connect();
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(sql)) {

            prepareStatement.setString(1, username);
            ResultSet rs = prepareStatement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("avatarPath"),
                        rs.getInt("score"),
                        rs.getInt("survivalTime"),
                        rs.getInt("totalKills")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void saveUser(User user) throws SQLException {
        Connection conn = DatabaseManager.connect();
        String sql = "INSERT INTO users (username, password, question, answer, avatarPath, score, survivalTime, totalKills) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        prepareStatement.setString(1, user.username);
        prepareStatement.setString(2, user.password);
        prepareStatement.setString(3, user.question);
        prepareStatement.setString(4, user.answer);
        prepareStatement.setString(5, user.avatarPath);
        prepareStatement.setInt(6, user.score);
        prepareStatement.setInt(7, user.survivalTime);
        prepareStatement.setInt(8, user.totalKills);
        prepareStatement.executeUpdate();
    }
}
