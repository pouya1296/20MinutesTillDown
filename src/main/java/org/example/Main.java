package org.example;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;
import org.example.view.LanguageSelectionScreen;

//TODO hint menu cheat codes

public class Main {
    public static void main(String[] args){
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("20min");
        config.useVsync(true);
        config.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public void filesDropped(String[] files) {
                //TODO age khoda bekhad mishe
            }
        });
        config.setWindowedMode(2400, 1800);
        new Lwjgl3Application(new LanguageSelectionScreen(), config);
    }
}