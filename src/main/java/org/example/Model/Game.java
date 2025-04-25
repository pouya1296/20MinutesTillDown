package org.example.Model;

public class Game {
    public boolean grayScaleMode;
    public boolean autoReload;
    public boolean soundEffect;
    public String username;

    public Game(boolean grayScaleMode, boolean autoReload, boolean soundEffect, String username) {
        this.grayScaleMode = grayScaleMode;
        this.autoReload = autoReload;
        this.soundEffect = soundEffect;
        this.username = username;
    }

}
