package org.example.controller;

import org.example.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:C:/Users/ASUS/users.db");
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

    public static void updateUser(User user) throws SQLException {
        Connection conn = DatabaseManager.connect();
        String sql = "UPDATE users SET password = ?, question = ?, answer = ?, avatarPath = ?, score = ?, survivalTime = ?, totalKills = ? WHERE username = ?";
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        prepareStatement.setString(1, user.password);
        prepareStatement.setString(2, user.question);
        prepareStatement.setString(3, user.answer);
        prepareStatement.setString(4, user.avatarPath);
        prepareStatement.setInt(5, user.score);
        prepareStatement.setInt(6, user.survivalTime);
        prepareStatement.setInt(7, user.totalKills);
        prepareStatement.setString(8, user.username);
        prepareStatement.executeUpdate();
    }

    public static void updateUsername(String oldUsername, String newUsername) throws SQLException {
        Connection conn = DatabaseManager.connect();
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        prepareStatement.setString(1, newUsername);
        prepareStatement.setString(2, oldUsername);
        prepareStatement.executeUpdate();
    }

    public static boolean deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";

        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(sql);
             ResultSet rs = prepareStatement.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("avatarPath"),
                        rs.getInt("score"),
                        rs.getInt("survivalTime"),
                        rs.getInt("totalKills")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
