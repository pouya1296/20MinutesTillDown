package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreGameMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton heroMenuButton, weaponMenuButton, timeMenuButton, newGameButton, backButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\PreGame.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        heroMenuButton = new TextButton("Hero Menu", skin);
        weaponMenuButton = new TextButton("Weapon Menu", skin);
        timeMenuButton = new TextButton("Time Menu", skin);
        newGameButton = new TextButton("New Game", skin);
        backButton = new TextButton("Back to Main Menu", skin);

        addClick();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(heroMenuButton).size(250, 50).pad(10);
        table.row();
        table.add(weaponMenuButton).size(250, 50).pad(10);
        table.row();
        table.add(timeMenuButton).size(250, 50).pad(10);
        table.row();
        table.add(newGameButton).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);

        stage.addActor(table);
        updateSelection();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void addClick() {
        heroMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HeroMenu());
            }
        });

        weaponMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WeaponMenu());
            }
        });

        timeMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new TimeMenu());
            }
        });

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
    }

    private void updateSelection() {
        heroMenuButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        weaponMenuButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        timeMenuButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        newGameButton.setColor(index == 3 ? Color.RED : Color.WHITE);
        backButton.setColor(index == 4 ? Color.RED : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            index = (index + 1) % 5;
        } else if (keycode == Input.Keys.UP) {
            index = (index - 1 + 5) % 5;
        } else if (keycode == Input.Keys.ENTER) {
            if (index == 0) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HeroMenu());
            } else if (index == 1) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WeaponMenu());
            } else if (index == 2) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new TimeMenu());
            } else if (index == 3) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu());
            } else {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }
        updateSelection();
        return true;
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
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
