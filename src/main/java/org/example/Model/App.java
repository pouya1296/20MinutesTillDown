package org.example.Model;

import org.example.view.*;

public class App {
    public String[] avatarPaths = new String[7];
    public static Menu currentMenu;
    public org.example.view.Menu[] menus = new org.example.view.Menu[9];
    public static int languageNumber = 0;
    public static User currentUser = null;

    public App() {
        for (int i = 0; i < 7; i++) {
            this.avatarPaths[i] = "\"C:\\Users\\ASUS\\Desktop\\project assets\\avatars\\avatar" + (i + 1) + ".jpg\"";
        }
        menus[Menu.REGISTER_MENU.value] = new StartMenu();
        menus[Menu.EXIT_MENU.value] = new ExitMenu();
        menus[Menu.LOGIN_MENU.value] = new LoginMenu();
        menus[Menu.PROFILE_MENU.value] = new ProfileMenu();
        menus[Menu.SETTINGS_MENU.value] = new SettingsMenu();
        menus[Menu.MAIN_MENU.value] = new MainMenu();
        menus[Menu.GAME_MENU.value] = new GameMenu();
        menus[Menu.PRE_GAME_MENU.value] = new PreGameMenu();
        menus[Menu.SCOREBOARD_MENU.value] = new ScoreboardMenu();
        currentMenu = Menu.REGISTER_MENU;
    }

    public static String getAvatarPath() {
        int randomIndex = (int) (Math.random() * 7);
        return "C:\\Users\\ASUS\\IdeaProjects\\untitled\\src\\main\\java\\org\\example\\view\\project assets\\avatars\\avatar" + (randomIndex + 1) + ".jpg";
    }
}
