package org.example.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class AppView {
    public static void startMenu(Screen screen) {
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }
}
