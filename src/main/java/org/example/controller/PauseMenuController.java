package org.example.controller;

import org.example.Model.App;
import org.example.Model.User;
import org.example.view.PauseMenu;
import org.example.view.PreGameMenu;
import org.example.view.StartMenu;

import java.sql.SQLException;

public class PauseMenuController {
    public static void giveUpHandle(PauseMenu pauseMenu) {
        if (App.currentUser.isGuest)
            ChangeMenuController.changeMenu(pauseMenu, new StartMenu());
        else {
            org.example.Model.Game game = App.currentGame;
            User user = App.currentUser;
            if (user.time < (int)game.time)
                user.time = (int)game.time;
            //score
            user.totalKills += game.kills;
            try {
                DatabaseManager.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ChangeMenuController.changeMenu(pauseMenu, new PreGameMenu());
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
