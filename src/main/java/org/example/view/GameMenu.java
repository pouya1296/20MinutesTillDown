package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import org.example.Model.Bullet;
import org.example.Model.Game;
import org.example.Model.Tree;
import org.example.Model.enums.Hero;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GameMenu implements Menu, InputProcessor, Screen {
    public transient OrthographicCamera camera;
    public transient SpriteBatch batch;
    public Game game;
    public int speed;
    public Hero hero;
    public transient Texture idlePlayerTexture;
    public transient Texture runningPlayerTexture;
    public boolean isPlayerMoving;
    public int[][] map;
    public transient Texture tileTexture;
    public Map<String, Integer> keys;
    public transient ShapeRenderer hpBarRenderer;
    public int maxAmmo;
    public transient Texture weaponIdleTexture;
    public transient Texture[] weaponReloadTextures;
    public int currentReloadFrame = 0;
    public List<Bullet> bullets = new ArrayList<>();
    public Texture treeTexture;

    public enum WeaponState {IDLE, SHOOTING, RELOADING}

    private OrthographicCamera hudCamera;
    public WeaponState weaponState;
    public float reloadTimer = 0;
    public transient BitmapFont font;
    public transient Texture bulletTexture;

    public GameMenu(Game game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(game.x, game.y, 0);
        camera.update();
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.update();

        if (font == null)
            font = new BitmapFont();
        this.hero = game.hero;
        speed = hero.speed;
        this.isPlayerMoving = false;
        idlePlayerTexture = new Texture(Gdx.files.internal(hero.idlePath));
        runningPlayerTexture = new Texture(Gdx.files.internal(hero.runningPath));
        this.tileTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_ForestTiles_0.png"));
        this.bulletTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\bullet.png"));
        map = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
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
        treeTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_TreeMonster_0.png"));
        for (int i = 0; i < 50; i++) {
            float x = (float) (Math.random() * 6400);
            float y = (float) (Math.random() * 6400);
            game.trees.add(new Tree(x, y, "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_TreeMonster_0.png"));
        }
    }

    public void endGame() {
    }

    public void update(float delta) {
        game.time += delta;
        float saveX = game.x;
        float saveY = game.y;
        if (game.time >= game.totalTime) {
            endGame();
        }
        if (Gdx.input.isKeyJustPressed(keys.get("PAUSE"))) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            //((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new PauseMenu());
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new PauseMenu());
        }
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
            if (Gdx.input.isKeyJustPressed(keys.get("FIRE")) || Gdx.input.isButtonJustPressed(keys.get("FIRE")) && game.ammo > 0) {
                weaponState = WeaponState.SHOOTING;
                game.ammo -= 1;
                Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(mousePos);
                bullets.add(new Bullet(game.x + 10, game.y + 10, mousePos.x, mousePos.y));
            } else {
                weaponState = WeaponState.IDLE;
            }
        }

        if ((Gdx.input.isKeyJustPressed(keys.get("RELOAD")) && game.ammo < maxAmmo) || (game.ammo == 0 && game.autoReload)) {
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
        float playerSize = 32;
        for (Tree tree : game.trees) {
            if (game.x + playerSize > tree.x && game.x < tree.x + 64 &&
                    game.y + playerSize > tree.y && game.y < tree.y + 64) {
                game.x = saveX;
                game.y = saveY;
                break;
            }
        }
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(delta, game);
            for (Tree tree : game.trees) {
                if (bullet.x + 8 > tree.x && bullet.x < tree.x + 64 &&
                        bullet.y + 8 > tree.y && bullet.y < tree.y + 64) {
                    bullet.active = false;
                    break;
                }
            }
            if (!bullet.active)
                bulletIterator.remove();
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
//        Pixmap pixmap = new Pixmap(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\cursor.png"));
//        Pixmap original = new Pixmap(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\cursor.png"));
//        Pixmap converted = new Pixmap(original.getWidth(), original.getHeight(), Pixmap.Format.RGBA8888);
//        converted.drawPixmap(original, 0, 0);
//        Cursor cursor = Gdx.graphics.newCursor(converted, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
//        Gdx.graphics.setCursor(cursor);
//        original.dispose();
//        pixmap.dispose();
    }

    @Override
    public void render(float delta) {
        update(delta);
        camera.position.set((game.x), (game.y), 0);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawMobile();

        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        drawStill();
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

    }

    private void drawStill() {
        font.draw(batch, "HP: " + game.hp + " / " + hero.maxHP, 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Kills: " + game.kills, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 40);
        font.draw(batch, "Ammo: " + game.ammo + " / " + game.weapon.maxAmmo, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 70);
        int remainingTime = Math.max(0, (int) (game.totalTime - game.time));
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        String timeText = String.format("Time: %02d:%02d", minutes, seconds);
        font.draw(batch, timeText, (float) Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 40);
    }

    private void drawMobile() {
        for (int x = -49; x < 50; x++) {
            for (int y = -49; y < 50; y++) {
                float tileX = x * 64;
                float tileY = y * 64;
                if (map[x + 49][y + 49] == 0) {
                    batch.draw(tileTexture, tileX, tileY, 64, 64);
                }
            }
        }
        for (Tree tree : game.trees) {
            batch.draw(treeTexture, tree.x, tree.y);
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
        batch.draw(currentWeaponTexture, game.x + 7, game.y + 7);
        for (Bullet bullet : bullets) {
            batch.draw(bulletTexture, bullet.x, bullet.y, 8, 8);
        }
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
