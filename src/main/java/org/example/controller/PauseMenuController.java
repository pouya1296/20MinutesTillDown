package org.example.controller;

import org.example.Model.App;
import org.example.Model.User;
import org.example.view.*;

import java.sql.SQLException;

public class PauseMenuController {
    public static void winHandle(GameMenu gameMenu) {
        if (App.currentUser.isGuest)
            ChangeMenuController.changeMenu(gameMenu, new StartMenu());
        else {
            org.example.Model.Game game = App.currentGame;
            User user = App.currentUser;
            if (user.survivalTime < (int) game.time)
                user.survivalTime = (int) game.time;
            //score
            user.totalKills += game.kills;
            user.score += (int) (game.time * game.kills);
            game.end = true;
            GameDataManager.saveGame(game);
            try {
                DatabaseManager.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ChangeMenuController.changeMenu(gameMenu, new EndGameView(game, 1));
        }
    }

    public static void dieHandle(GameMenu gameMenu) {
        if (App.currentUser.isGuest)
            ChangeMenuController.changeMenu(gameMenu, new StartMenu());
        else {
            org.example.Model.Game game = App.currentGame;
            User user = App.currentUser;
            if (user.survivalTime < (int) game.time)
                user.survivalTime = (int) game.time;
            //score
            user.totalKills += game.kills;
            user.score += (int) (game.time * game.kills);
            game.end = true;
            GameDataManager.saveGame(game);
            try {
                DatabaseManager.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ChangeMenuController.changeMenu(gameMenu, new EndGameView(game, 2));
        }

    }

    public static void giveUpHandle(PauseMenu pauseMenu) {
        if (App.currentUser.isGuest)
            ChangeMenuController.changeMenu(pauseMenu, new StartMenu());
        else {
            org.example.Model.Game game = App.currentGame;
            User user = App.currentUser;
            if (user.survivalTime < (int) game.time)
                user.survivalTime = (int) game.time;
            //score
            user.totalKills += game.kills;
            user.score += (int) (game.time * game.kills);
            game.end = true;
            GameDataManager.saveGame(game);
            try {
                DatabaseManager.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ChangeMenuController.changeMenu(pauseMenu, new EndGameView(game, 3));
        }
    }

    public static void saveAndExitHandle(PauseMenu pauseMenu) {
        if (App.currentUser.isGuest)
            ChangeMenuController.changeMenu(pauseMenu, new StartMenu());
        else {
            org.example.Model.Game game = App.currentGame;
            GameDataManager.saveGame(game);
            ChangeMenuController.changeMenu(pauseMenu, new PreGameMenu());
        }
    }

}
