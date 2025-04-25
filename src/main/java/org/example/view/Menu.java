package org.example.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public interface Menu {
    public void handleMenu();

    public default void changeScreen(Screen newScreen) {
        Screen currentScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();

        ((Game) Gdx.app.getApplicationListener()).setScreen(newScreen);

        if (currentScreen != null && !(currentScreen instanceof KeyboardMenu)) {
            currentScreen.dispose();
        }
    }

}
