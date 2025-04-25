package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class User {
    public String username;
    public String password;
    public String question;
    public String answer;
    public Texture texture;
    public String avatarPath;
    public int score;
    public int survivalTime;
    public int totalKills;
    public boolean grayScaleMode;
    public boolean autoReload;
    public boolean soundEffect;

    public User(String username, String password, String question, String answer, String avatarPath, int score, int survivalTime, int totalKills) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatarPath = avatarPath;
        texture = new Texture(Gdx.files.internal(avatarPath));
        this.score = 0;
        this.survivalTime = survivalTime;
        this.totalKills = totalKills;
        this.grayScaleMode = false;
        this.autoReload = false;
        this.soundEffect = false;
    }
}
