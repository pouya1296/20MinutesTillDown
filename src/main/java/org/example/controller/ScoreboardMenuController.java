package org.example.controller;

import org.example.Model.User;
import org.example.view.ScoreboardDisplay;
import org.example.view.ScoreboardMenu;

import java.sql.SQLException;
import java.util.List;

public class ScoreboardMenuController {
    public static void sortUsersByScore(ScoreboardMenu scoreboardMenu) throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));
        ChangeMenuController.changeMenu(scoreboardMenu, new ScoreboardDisplay(users));
    }

    public static void sortUsersByUsername(ScoreboardMenu scoreboardMenu) throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));
        ChangeMenuController.changeMenu(scoreboardMenu, new ScoreboardDisplay(users));
    }

    public static void sortUsersByKills(ScoreboardMenu scoreboardMenu) throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getTotalKills(), u1.getTotalKills()));
        ChangeMenuController.changeMenu(scoreboardMenu, new ScoreboardDisplay(users));
    }

    public static void sortUsersBySurvivalTime(ScoreboardMenu scoreboardMenu) throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getSurvivalTime(), u1.getSurvivalTime()));
        ChangeMenuController.changeMenu(scoreboardMenu, new ScoreboardDisplay(users));
    }
}
