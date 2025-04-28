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
import org.example.Model.App;
import org.example.controller.ChangeMenuController;

public class TimeMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton twoMinutesButton, fiveMinutesButton, tenMinutesButton, thirtyMinutesButton, backButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Label currentTimeLabel;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\PreGame.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        currentTimeLabel = new Label("Current Time: " + App.currentUser.time + " seconds", skin);
        currentTimeLabel.setFontScale(2);

        twoMinutesButton = new TextButton("120 seconds", skin);
        fiveMinutesButton = new TextButton("300 seconds", skin);
        tenMinutesButton = new TextButton("600 seconds", skin);
        thirtyMinutesButton = new TextButton("1200 seconds", skin);
        backButton = new TextButton("Back to PreGame Menu", skin);

        twoMinutesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentUser.time = 120;
                updateTimeDisplay();
            }
        });

        fiveMinutesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentUser.time = 300;
                updateTimeDisplay();
            }
        });

        tenMinutesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentUser.time = 600;
                updateTimeDisplay();
            }
        });

        thirtyMinutesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentUser.time = 1200;
                updateTimeDisplay();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(TimeMenu.this, new PreGameMenu());
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(currentTimeLabel).colspan(2).padBottom(20);
        table.row();
        table.add(twoMinutesButton).size(250, 50).pad(10);
        table.row();
        table.add(fiveMinutesButton).size(250, 50).pad(10);
        table.row();
        table.add(tenMinutesButton).size(250, 50).pad(10);
        table.row();
        table.add(thirtyMinutesButton).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);

        stage.addActor(table);
        updateSelection();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void updateTimeDisplay() {
        currentTimeLabel.setText("Current Time: " + App.currentUser.time + " seconds");
    }

    private void updateSelection() {
        twoMinutesButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        fiveMinutesButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        tenMinutesButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        thirtyMinutesButton.setColor(index == 3 ? Color.RED : Color.WHITE);
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
                App.currentUser.time = 120;
            } else if (index == 1) {
                App.currentUser.time = 300;
            } else if (index == 2) {
                App.currentUser.time = 600;
            } else if (index == 3) {
                App.currentUser.time = 1200;
            } else {
                ChangeMenuController.changeMenu(TimeMenu.this, new PreGameMenu());
            }
            updateTimeDisplay();
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
