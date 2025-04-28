package org.example.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class ChangeMenuController {
    public static void changeMenu(Screen currentScreen, Screen nextScreen) {
        currentScreen.dispose();
        ((Game) Gdx.app.getApplicationListener()).setScreen(nextScreen);
    }
}
