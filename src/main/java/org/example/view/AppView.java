package org.example.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class AppView {
    public static void startMenu(Screen screen) {
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }
}
