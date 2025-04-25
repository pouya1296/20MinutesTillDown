package org.example.Model;

import org.example.view.*;

public class App {
    public static Menu currentMenu;
    public org.example.view.Menu[] menus = new org.example.view.Menu[9];
    public static int languageNumber = 0;
    public static User currentUser = null;

    public App() {

    }

    public static String getAvatarPath() {
        int randomIndex = (int) (Math.random() * 7);
        return "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\avatars\\avatar" + (randomIndex + 1) + ".jpg";
    }
}
