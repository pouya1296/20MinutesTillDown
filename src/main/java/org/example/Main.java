package org.example;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.example.view.LanguageSelectionScreen;
import java.sql.SQLException;

//TODO hint menu cheat codes

public class Main {
    public static void main(String[] args) throws SQLException {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 2560;
        config.height = 1600;
        new LwjglApplication(new LanguageSelectionScreen(), config);
    }
}