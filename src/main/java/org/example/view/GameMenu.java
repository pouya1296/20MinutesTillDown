package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import org.example.Model.*;
import org.example.Model.enums.Ability;
import org.example.Model.enums.Enemy;
import org.example.Model.enums.Hero;
import org.example.controller.GameMenuController;
import org.example.controller.PauseMenuController;

import java.util.Map;

public class GameMenu implements Menu, InputProcessor, Screen {
    public transient OrthographicCamera camera;
    public transient SpriteBatch batch;
    public Game game;
    public Hero hero;
    public transient Texture idlePlayerTexture;
    public transient Texture runningPlayerTexture;
    public boolean isPlayerMoving;
    public transient Texture tileTexture;
    public Map<String, Integer> keys;
    public transient ShapeRenderer hpBarRenderer;
    public transient ShapeRenderer xpBarRenderer;
    public transient Texture weaponIdleTexture;
    public transient Texture[] weaponReloadTextures;
    public int currentReloadFrame = 0;
    public Texture treeTexture;
    public boolean autoReloadStart;
    public Texture dropTexture;
    public Texture hitTexture;
    private ShaderProgram grayscaleShader;
    public Sound shootSound;
    public Sound death;
    public Sound hitSound;
    public Sound levelSound;
    public Sound winSound;
    public Sound loseSound;



    public enum WeaponState {IDLE, SHOOTING, RELOADING}

    private OrthographicCamera hudCamera;
    public WeaponState weaponState;
    public float reloadTimer = 0;
    public transient BitmapFont font;
    public transient Texture bulletTexture;
    public Animation<TextureRegion> tentacleWalkAnimation;
    public float eyebatSpawnTimer = 0f;
    public Animation<TextureRegion> eyebatAnimation;
    public Animation<TextureRegion> elimintationAnimation;
    public Texture eyebatDropTexture;
    public Texture tentacleDropTexture;
    public Texture elderDropTexture;

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

        this.isPlayerMoving = false;
        idlePlayerTexture = new Texture(Gdx.files.internal(hero.idlePath));
        runningPlayerTexture = new Texture(Gdx.files.internal(hero.runningPath));
        this.tileTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_ForestTiles_0.png"));
        this.bulletTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\bullet.png"));

        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        this.keys = game.keys;
        hpBarRenderer = new ShapeRenderer();
        xpBarRenderer = new ShapeRenderer();
        weaponState = WeaponState.IDLE;
        weaponIdleTexture = new Texture(Gdx.files.internal(game.weapon.paths[0]));
        weaponReloadTextures = new Texture[game.weapon.paths.length - 1];
        for (int i = 1; i < game.weapon.paths.length; i++) {
            weaponReloadTextures[i - 1] = new Texture(Gdx.files.internal(game.weapon.paths[i]));
        }
        treeTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_TreeMonster_0.png"));
        TextureRegion[] tentacleFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            Texture t = new Texture(Gdx.files.internal(org.example.Model.enums.Enemy.TENTACLE.texturePaths[i]));
            tentacleFrames[i] = new TextureRegion(t);
        }
        tentacleWalkAnimation = new Animation<>(0.1F, tentacleFrames);
        TextureRegion[] eyebatFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            Texture t = new Texture(Gdx.files.internal(Enemy.EYE_BAT.texturePaths[i]));
            eyebatFrames[i] = new TextureRegion(t);
        }
        autoReloadStart = true;
        eyebatAnimation = new Animation<>(0.1F, eyebatFrames);
        dropTexture = new Texture(Gdx.files.internal(Enemy.TENTACLE.texturePaths[5]));
        TextureRegion[] elimintationFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            Texture t = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\FireballExplosion_" + (i + 1) + ".png"));
            elimintationFrames[i] = new TextureRegion(t);
        }
        elimintationAnimation = new Animation<>(0.1F, elimintationFrames);
        game.shieldRenderer = new ShapeRenderer();
        hitTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\FireballExplosion_0.png"));
        elderDropTexture = new Texture("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\ElderBrain_Em.png");
        tentacleDropTexture = new Texture("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\EyeMonster_Em.png");
        eyebatDropTexture = new Texture("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\BrainMonster_Em.png");
        loadShaders();
        loadSounds();
    }

    public void endGame() {
    }

    public void loadSounds() {
        shootSound = Gdx.audio.newSound(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\sound\\AudioClip\\Standard_Weapon_Whoosh_01.wav"));
        death = Gdx.audio.newSound(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\sound\\AudioClip\\Bat_Death_02.wav"));
        levelSound = Gdx.audio.newSound(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\sound\\AudioClip\\Special & Powerup (10).wav"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\sound\\AudioClip\\You Lose (4).wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\sound\\AudioClip\\You Win (2).wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\sound\\AudioClip\\single_shot.wav"));
    }

    public void update(float delta) {
        cheatCodeHandle();
        handleAbility(delta);

        if (game.autoAim) {
            handleAutoAim();
        }

        game.time += delta;
        int saveHp = game.hp;
        game.elderDashTimer += delta;

        if (game.invincibleTimer > 0f) {
            game.invincibleTimer -= delta;
            if (game.invincibleTimer < 0f)
                game.invincibleTimer = 0f;
        }
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
        handleInput(delta);
        float playerSize = (float) idlePlayerTexture.getHeight() / 2;
        game.invincibleTimer = GameMenuController.playerTreeHandle(playerSize, saveX, saveY, game, game.invincibleTimer);
        GameMenuController.playerShield(game, saveX, saveY, hitSound);
        GameMenuController.bulletHandle(delta, game, game.bullets, treeTexture, tentacleWalkAnimation, eyebatAnimation, death);
        GameMenuController.dropHandle(game, idlePlayerTexture);
        game.enemySpawnTimer += delta;
        GameMenuController.tentacleSpawn(game);
        eyebatSpawnTimer = GameMenuController.eyebatSpawn(delta, game, eyebatSpawnTimer);
        GameMenuController.eyeBatShoot(delta, game, game.eyebatBullets, hitSound);
        GameMenuController.elderSpawn(game);
        GameMenuController.shieldHandle(game, delta, death);

        for (org.example.Model.Enemy enemy : game.enemies) {
            enemy.update(delta, game);
        }

        GameMenuController.playerEnemy(game, tentacleWalkAnimation, eyebatAnimation, hitSound);
        GameMenuController.levelHandle(game, levelSound);
        GameMenuController.removeEnemyHandle(game, elderDropTexture, tentacleDropTexture, eyebatDropTexture);
        if(game.hp<= 0 && !game.immortal){
            GameMenuController.playSound(game, loseSound);
            PauseMenuController.dieHandle(this);
        }
        else if(game.time >= game.totalTime){
            GameMenuController.playSound(game, winSound);
            PauseMenuController.winHandle(this);
        }

    }

    private void handleAutoAim() {
        org.example.Model.Enemy nearestEnemy = GameMenuController.findNearestEnemy(game);
        if (nearestEnemy != null) {

            Vector3 enemyPos = new Vector3(
                    nearestEnemy.x + 16,
                    nearestEnemy.y + 16,
                    0
            );
            camera.project(enemyPos);

            Gdx.input.setCursorPosition((int) enemyPos.x, Gdx.graphics.getHeight() - (int) enemyPos.y);
        }
    }

    private void cheatCodeHandle() {
        if (Gdx.input.isKeyJustPressed(keys.get("TIME_CHEAT"))) {
            game.time += 60;
        }
        if (Gdx.input.isKeyJustPressed(keys.get("BOSS_CHEAT"))) {
            game.time = game.totalTime / 2;
        }
        if (Gdx.input.isKeyJustPressed(keys.get("LEVEL_CHEAT"))) {
            GameMenuController.addLevel(game, levelSound);
        }
        if (Gdx.input.isKeyJustPressed(keys.get("HP_CHEAT"))) {
            game.hp++;
            if (game.hp > game.maxHp) {
                game.hp = game.maxHp;
            }
        }
        if(Gdx.input.isKeyJustPressed(keys.get("IMMORTAL_CHEAT"))) {
            game.immortal = true;
        }
        if(Gdx.input.isKeyJustPressed(keys.get("AUTO AIM"))) {
            game.autoAim = !game.autoAim;
        }
    }

    private void handleAbility(float delta) {
        if(game.abilitiesQueue.contains(Ability.VITALITY))
            GameMenuController.vitalityHandle(game);
        if(game.abilitiesQueue.contains(Ability.PROCREASE))
            GameMenuController.procreaseHandle(game);
        if(game.abilitiesQueue.contains(Ability.AMOCREASE))
            GameMenuController.ammoCreaseHandle(game);
        if(game.abilitiesQueue.contains(Ability.DAMAGER))
            GameMenuController.damagerHandle(game, delta);
        if(game.abilitiesQueue.contains(Ability.SPEEDY))
            GameMenuController.speedyHandle(game, delta);
    }

    private void loadShaders() {
        if (Gdx.gl20 == null) return;

        String vertexShader = Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\blackandwhite.glsl").readString();

        grayscaleShader = new ShaderProgram(vertexShader, fragmentShader);
    }

    public void handleInput(float delta) {
        if (weaponState != WeaponState.RELOADING) {
            if (Gdx.input.isKeyJustPressed(keys.get("FIRE")) || Gdx.input.isButtonJustPressed(keys.get("FIRE")) && game.ammo > 0) {
                weaponState = GameMenuController.shootHandle(game, camera);
                GameMenuController.playSound(game, shootSound);
            } else {
                weaponState = WeaponState.IDLE;
            }
        }

        if ((Gdx.input.isKeyJustPressed(keys.get("RELOAD")) && game.ammo < game.maxAmmo) || (game.ammo == 0 && game.autoReload && autoReloadStart)) {
            weaponState = WeaponState.RELOADING;
            reloadTimer = 0;
            currentReloadFrame = 0;
            autoReloadStart = false;
        }
        if (weaponState == WeaponState.RELOADING) {
            reloadTimer += Gdx.graphics.getDeltaTime();
            if (reloadTimer > game.weapon.reloadTime / weaponReloadTextures.length) {
                reloadTimer = 0;
                if (currentReloadFrame < weaponReloadTextures.length - 1) {
                    currentReloadFrame++;
                } else {
                    weaponState = WeaponState.IDLE;
                    game.ammo = game.maxAmmo;
                    autoReloadStart = true;
                }
            }
        }
        isPlayerMoving = GameMenuController.movementHandle(delta, game, game.speed, keys);
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
        if(game.isBlackWhite ) {
            batch.setShader(grayscaleShader);
        }
        drawMobile();
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        if(game.isBlackWhite ) {
            batch.setShader(grayscaleShader);
        }
        drawStill();
        batch.end();
        float hpPercent = (float) game.hp / game.maxHp;
        hpBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hpBarRenderer.setColor(Color.WHITE);
        hpBarRenderer.rect(20, Gdx.graphics.getHeight() - 40, 100, 20);
        if(game.isBlackWhite)
            hpBarRenderer.setColor(Color.BLACK);
        else
        hpBarRenderer.setColor(Color.RED);

        hpBarRenderer.rect(20, Gdx.graphics.getHeight() - 40, 100 * hpPercent, 20);
        hpBarRenderer.end();
        xpBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
        xpBarRenderer.setColor(Color.WHITE);
        xpBarRenderer.rect(20, Gdx.graphics.getHeight() - 100, 100, 20);
        xpBarRenderer.end();
        xpBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(game.isBlackWhite)
            xpBarRenderer.setColor(Color.BLACK);
        else
        xpBarRenderer.setColor(Color.BLUE);

        xpBarRenderer.rect(20, Gdx.graphics.getHeight() - 100, 100 * ((float) (game.xp) / (game.level * 20)), 20);
        xpBarRenderer.end();
        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        if (game.shieldActive) {
            game.shieldRenderer.setProjectionMatrix(camera.combined);
            game.shieldRenderer.begin(ShapeRenderer.ShapeType.Line);
            if(game.isBlackWhite)
                game.shieldRenderer.setColor(Color.BLACK);
            else
              game.shieldRenderer.setColor(Color.RED);

            game.shieldRenderer.rect(
                    (Gdx.graphics.getWidth() / 2) - game.shieldHalfWidth,
                    (Gdx.graphics.getHeight() / 2) - game.shieldHalfHeight,
                    game.shieldHalfWidth * 2,
                    game.shieldHalfHeight * 2
            );
            game.shieldRenderer.end();
        }
        batch.setShader(null);

    }

    private void drawStill() {
        font.draw(batch, "HP: " + game.hp + " / " + game.maxHp, 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Kills: " + game.kills, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 40);
        font.draw(batch, "Ammo: " + game.ammo + " / " + game.maxAmmo, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 70);
        font.draw(batch, "xp: " + (game.xp) + " / " + game.level * 20, 20, Gdx.graphics.getHeight() - 110);

        int remainingTime = Math.max(0, (int) (game.totalTime - game.time));
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        String timeText = String.format("Time: %02d:%02d", minutes, seconds);
        font.draw(batch, timeText, (float) Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 40);
    }

    private void drawMobile() {
        for (int x = -99; x < 100; x++) {
            for (int y = -99; y < 100; y++) {
                float tileX = x * 64;
                float tileY = y * 64;
                if (game.map[x + 99][y + 99] == 0) {
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
        if (game.isHit) {
            batch.draw(hitTexture, game.x, game.y);
            game.isHit = false;
        }
        Texture currentWeaponTexture;
        if (weaponState == WeaponState.RELOADING) {
            currentWeaponTexture = weaponReloadTextures[currentReloadFrame];
        } else {
            currentWeaponTexture = weaponIdleTexture;
        }
        batch.draw(currentWeaponTexture, game.x + 7, game.y + 7);
        for (Bullet bullet : game.bullets) {
            batch.draw(bulletTexture, bullet.x, bullet.y, 8, 8);
        }
        for (org.example.Model.Enemy m : game.enemies) {
            if (m instanceof TentacleMonster)
                m.render(batch, tentacleWalkAnimation, elimintationAnimation);
            else if (m instanceof EyeBat)
                m.render(batch, eyebatAnimation, elimintationAnimation);
            else
                m.render(batch, eyebatAnimation, elimintationAnimation);
        }
        for (Drop drop : game.drops) {
            drop.updateBounds();
            if (drop.textureID == 0)
                drop.render(batch, elderDropTexture);
            else if (drop.textureID == 1)
                drop.render(batch, tentacleDropTexture);
            else
                drop.render(batch, eyebatDropTexture);
        }
        for (Bullet b : game.eyebatBullets) {
            batch.draw(bulletTexture, b.x, b.y, 8, 8);
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
        shootSound.dispose();
        death.dispose();
    }

    @Override
    public void handleMenu() {

    }
}
