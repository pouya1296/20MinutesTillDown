package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import org.example.Model.enums.Hero;
import org.example.Model.enums.Weapon;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Integer> keys = new HashMap<>();
    public Hero hero;
    public Weapon weapon;
    public int time;
    public boolean isGuest;

    public User(String username, String password, String question, String answer, String avatarPath, int score, int survivalTime, int totalKills, boolean isGuest) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatarPath = avatarPath;
        texture = new Texture(Gdx.files.internal(avatarPath));
        this.score = score;
        this.survivalTime = survivalTime;
        this.totalKills = totalKills;
        this.grayScaleMode = false;
        this.autoReload = false;
        this.soundEffect = false;
        InitializeKeys();
        this.hero = Hero.SHANA;
        this.time = 120;
        this.weapon = Weapon.REVOLVER;
        this.isGuest = isGuest;
    }

    private void InitializeKeys() {
        keys.put("UP", Input.Keys.W);
        keys.put("DOWN", Input.Keys.S);
        keys.put("LEFT", Input.Keys.A);
        keys.put("RIGHT", Input.Keys.D);
        keys.put("FIRE", Input.Buttons.LEFT);
        keys.put("AUTO AIM", Input.Keys.SPACE);
        keys.put("RELOAD", Input.Keys.R);
        keys.put("PAUSE", Input.Keys.P);
        keys.put("TIME_CHEAT", Input.Keys.NUM_1);
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public int getTotalKills() {
        return totalKills;
    }
}
