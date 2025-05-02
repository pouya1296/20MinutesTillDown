package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.example.Model.enums.Ability;
import org.example.Model.enums.Hero;
import org.example.Model.enums.Weapon;
import org.example.view.GameMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    public boolean grayScaleMode;
    public boolean autoReload;
    public boolean soundEffect;
    public String username;
    public Map<String, Integer> keys = new HashMap<>();
    public ArrayList<Ability> abilities = new ArrayList<>();
    public org.example.Model.enums.Hero hero;
    public Weapon weapon;
    public float time;
    public float totalTime;
    public int kills;
    public Ability currentAbility;
    public boolean isBlackWhite;
    public float x;
    public float y;
    public float width;
    public float height;
    public int hp;
    public int ammo;
    public transient GameMenu gameMenu;
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Tree> trees = new ArrayList<>();


    public Game(User user) {
        this.grayScaleMode = user.grayScaleMode;
        this.autoReload = user.autoReload;
        this.soundEffect = user.soundEffect;
        this.username = user.username;
        this.keys.putAll(user.keys);
        this.hero = user.hero;
        this.weapon = user.weapon;
        this.currentAbility = null;
        isBlackWhite = false;
        this.totalTime = user.time;
        this.time = 0;
        this.kills = 0;
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.x = this.width / 2;
        this.y = this.height / 2;
        this.hp = hero.maxHP;
        this.ammo = weapon.maxAmmo;
        this.gameMenu = new GameMenu(this);

//        blackAndWhiteShader = new ShaderProgram(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\blackandwhite\\blackandwhite.glsl"),
//                Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\blackandwhite\\vertex.glsl"));
    }

    public void setKey(String direction, int keyCode) {
        keys.put(direction, keyCode);
    }

    public int getKey(String direction) {
        return keys.getOrDefault(direction, Input.Keys.UNKNOWN);
    }

}
