package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.example.Model.App;
import org.example.Model.Game;
import org.example.Model.enums.Hero;
import org.example.controller.ChangeMenuController;

import java.util.Map;


public class GameMenu implements Menu, InputProcessor, Screen {
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public Game game;
    public int speed;
    public Hero hero;
    public Texture idlePlayerTexture;
    public Texture runningPlayerTexture;
    public boolean isPlayerMoving;
    public int[][] map;
    public Texture tileTexture;
    public Map<String, Integer> keys;
    public ShapeRenderer hpBarRenderer;
    public int maxAmmo;
    private Texture weaponIdleTexture;
    private Texture[] weaponReloadTextures;
    private int currentReloadFrame = 0;
    public enum WeaponState { IDLE, SHOOTING, RELOADING }
    public WeaponState weaponState;
    public float reloadTimer = 0;
    public BitmapFont font;

    public GameMenu(Game game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(game.x, game.y, 0);
        camera.update();
        if (batch == null)
            batch = new SpriteBatch();
        if (font == null)
            font = new BitmapFont();
        this.hero = game.hero;
        speed = hero.speed;
        this.isPlayerMoving = false;
        idlePlayerTexture = new Texture(Gdx.files.internal(hero.idlePath));
        runningPlayerTexture = new Texture(Gdx.files.internal(hero.runningPath));
        this.tileTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_ForestTiles_0.png"));
        map = new int[100][100];
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                map[i][j] = 0;
            }
        }
        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        maxAmmo = game.weapon.maxAmmo;
        game.ammo = maxAmmo;
        this.keys = game.keys;
        hpBarRenderer = new ShapeRenderer();
        weaponState = WeaponState.IDLE;
        weaponIdleTexture = new Texture(Gdx.files.internal(game.weapon.paths[0]));
        weaponReloadTextures = new Texture[game.weapon.paths.length - 1];
        for (int i = 1; i < game.weapon.paths.length; i++) {
            weaponReloadTextures[i - 1] = new Texture(Gdx.files.internal(game.weapon.paths[i]));
        }
    }

    public void update(float delta) {
        isPlayerMoving = false;
        if (Gdx.input.isKeyPressed(keys.get("UP")) && Gdx.input.isKeyPressed(keys.get("LEFT"))) {
            game.x -= speed * delta;
            game.y += speed * delta;
            isPlayerMoving = true;
        } else if (Gdx.input.isKeyPressed(keys.get("UP")) && Gdx.input.isKeyPressed(keys.get("RIGHT"))) {
            game.x += speed * delta;
            game.y += speed * delta;
            isPlayerMoving = true;
        } else if (Gdx.input.isKeyPressed(keys.get("DOWN")) && Gdx.input.isKeyPressed(keys.get("LEFT"))) {
            game.x -= speed * delta;
            game.y -= speed * delta;
            isPlayerMoving = true;
        } else if (Gdx.input.isKeyPressed(keys.get("DOWN")) && Gdx.input.isKeyPressed(keys.get("RIGHT"))) {
            game.x += speed * delta;
            game.y -= speed * delta;
            isPlayerMoving = true;
        } else {
            if (Gdx.input.isKeyPressed(keys.get("UP"))) {
                game.y += speed * delta;
                isPlayerMoving = true;
            }
            if (Gdx.input.isKeyPressed(keys.get("DOWN"))) {
                game.y -= speed * delta;
                isPlayerMoving = true;
            }
            if (Gdx.input.isKeyPressed(keys.get("LEFT"))) {
                game.x -= speed * delta;
                isPlayerMoving = true;
            }
            if (Gdx.input.isKeyPressed(keys.get("RIGHT"))) {
                game.x += speed * delta;
                isPlayerMoving = true;
            }
        }
        if (weaponState != WeaponState.RELOADING) {
            if (Gdx.input.isKeyPressed(keys.get("FIRE")) && game.ammo >0) {
                weaponState = WeaponState.SHOOTING;
                game.ammo -= 1;
            } else {
                weaponState = WeaponState.IDLE;
            }
        }

        if (Gdx.input.isKeyPressed(keys.get("RELOAD")) && game.ammo <maxAmmo) {
            weaponState = WeaponState.RELOADING;
            reloadTimer = 0;
            currentReloadFrame = 0;
        }
        if (weaponState == WeaponState.RELOADING) {
            reloadTimer += Gdx.graphics.getDeltaTime();
            if (reloadTimer > game.weapon.reloadTime / weaponReloadTextures.length) {
                reloadTimer = 0;
                if (currentReloadFrame < weaponReloadTextures.length - 1) {
                    currentReloadFrame++;
                } else {
                    weaponState = WeaponState.IDLE;
                    game.ammo = maxAmmo;
                }
            }
        }
        if(Gdx.input.isKeyPressed(keys.get("PAUSE"))) {
            //((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new PauseMenu());
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new PauseMenu());
        }



    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        camera.position.set((game.x), (game.y), 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float tileX = x * 64;
                float tileY = y * 64;
                if (map[x][y] == 0) {
                    batch.draw(tileTexture, tileX, tileY, 64, 64);
                }
            }
        }
        if (isPlayerMoving)
            batch.draw(runningPlayerTexture, game.x, game.y);
        else
            batch.draw(idlePlayerTexture, game.x, game.y);
        Texture currentWeaponTexture;
        if (weaponState == WeaponState.RELOADING) {
            currentWeaponTexture = weaponReloadTextures[currentReloadFrame];
        } else {
            currentWeaponTexture = weaponIdleTexture;
        }
        batch.draw(currentWeaponTexture, game.x + 5, game.y + 5);
        batch.end();
        float hpPercent = (float) game.hp / hero.maxHP;
        hpBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hpBarRenderer.setColor(Color.WHITE);
        hpBarRenderer.rect(20, Gdx.graphics.getHeight() - 40, 100, 20);
        hpBarRenderer.setColor(Color.RED);
        hpBarRenderer.rect(20, Gdx.graphics.getHeight() - 40, 100 * hpPercent, 20);
        hpBarRenderer.end();
        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        batch.begin();
        font.draw(batch, "HP: " + game.hp + " / " + hero.maxHP, 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Kills: " + game.kills, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 40);
        font.draw(batch, "Ammo: " + game.ammo + " / " + game.weapon.maxAmmo, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 70);
        batch.end();


    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void handleMenu() {

    }
}
