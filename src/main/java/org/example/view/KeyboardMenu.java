package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Map;

public class KeyboardMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton backButton;
    private Map<String, Integer> keys;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public KeyboardMenu(Map<String, Integer> keys) {
        this.keys = keys;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\settingMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        table = new Table();
        table.setFillParent(true);

        for (Map.Entry<String, Integer> entry : keys.entrySet()) {
            Label actionLabel = new Label(entry.getKey(), skin);
            TextField keyField = new TextField(Input.Keys.toString(entry.getValue()), skin);
            keyField.setDisabled(true);

            TextButton changeButton = new TextButton("Change", skin);
            changeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean keyDown(int keycode) {
                            keys.put(entry.getKey(), keycode);
                            keyField.setText(Input.Keys.toString(keycode));
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    });
                }
            });

            table.add(actionLabel).pad(10);
            table.add(keyField).width(100).pad(10);
            table.add(changeButton).size(100, 40).pad(10);
            table.row();
        }

        backButton = new TextButton("Back to Settings menu", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsMenu());
            }
        });

        table.row();
        table.add(backButton).size(250, 50).padTop(20);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

