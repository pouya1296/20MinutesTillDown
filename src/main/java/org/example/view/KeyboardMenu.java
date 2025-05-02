package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.enums.Language;
import org.example.controller.ChangeMenuController;

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

            TextButton changeButton = new TextButton(Language.CHANGE.languages[App.languageNumber], skin);
            changeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean keyDown(int keycode) {
                            assignBinding(keycode);
                            return true;
                        }
                        @Override
                        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                            assignBinding(button);
                            return true;
                        }
                        private void assignBinding(int code) {
                            keys.put(entry.getKey(), code);
                            String name;
                            if (code == Input.Buttons.LEFT) {
                                name = "MOUSE_LEFT";
                            } else if (code == Input.Buttons.RIGHT) {
                                name = "MOUSE_RIGHT";
                            } else {
                                name = Input.Keys.toString(code);
                            }
                            keyField.setText(name);
                            Gdx.input.setInputProcessor(stage);
                        }
                    });
                }
            });


            table.add(actionLabel).pad(10);
            table.add(keyField).width(100).pad(10);
            table.add(changeButton).size(100, 40).pad(10);
            table.row();
        }

        backButton = new TextButton(Language.BACK_TO_SETTINGS.languages[App.languageNumber], skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(KeyboardMenu.this, new SettingsMenu());
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
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

