package org.example.Model.enums;

public enum Menu {
    REGISTER_MENU(0),
    MAIN_MENU(1),
    EXIT_MENU(2),
    LOGIN_MENU(3),
    SETTINGS_MENU(4),
    PROFILE_MENU(5),
    PRE_GAME_MENU(6),
    SCOREBOARD_MENU(7),
    GAME_MENU(8);

    public final int value;

    Menu(int value) {
        this.value = value;
    }
}
