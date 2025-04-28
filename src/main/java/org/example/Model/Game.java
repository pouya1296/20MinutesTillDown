package org.example.Model;

import com.badlogic.gdx.Input;
import org.example.Model.enums.Ability;
import org.example.Model.enums.Hero;
import org.example.Model.enums.Weapon;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public boolean grayScaleMode;
    public boolean autoReload;
    public boolean soundEffect;
    public String username;
    private final Map<String, Integer> keys = new HashMap<>();
    public org.example.Model.enums.Hero hero;
    public Weapon weapon;
    public int time;
    public Ability currentAbility;

    public Game(boolean grayScaleMode, boolean autoReload, boolean soundEffect, String username, Map<String, Integer> keys, Hero hero, Weapon weapon, int time) {
        this.grayScaleMode = grayScaleMode;
        this.autoReload = autoReload;
        this.soundEffect = soundEffect;
        this.username = username;
        this.keys.putAll(keys);
        this.hero = hero;
        this.weapon = weapon;
        this.currentAbility = null;
    }

    public void setKey(String direction, int keyCode) {
        keys.put(direction, keyCode);
    }

    public int getKey(String direction) {
        return keys.getOrDefault(direction, Input.Keys.UNKNOWN);
    }

}
