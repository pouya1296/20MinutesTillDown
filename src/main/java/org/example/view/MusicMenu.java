package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.enums.Language;
import org.example.controller.ChangeMenuController;
import org.example.controller.MusicManager;

public class MusicMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton track1Button, track2Button, track3Button, track4Button, track5Button, track6Button, track7Button, backButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\settingMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        createMusicButtons();
        Table table = createTable();
        stage.addActor(backgroundImage);
        stage.addActor(table);
        updateSelection();
        addClickListener();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void addClickListener() {
        track1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\akharin.mp3");
            }
        });

        track2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\bad.mp3");
            }
        });

        track3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\extasy.mp3");
            }
        });

        track4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\ghors.mp3");
            }
        });

        track5Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\lonely.mp3");
            }
        });

        track6Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\sweet.mp3");
            }
        });

        track7Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\tabestoon.mp3");
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(MusicMenu.this, new MainMenu());
            }
        });
    }


    private void createMusicButtons() {
        track1Button = new TextButton("Akharin Vasvase", skin);
        track2Button = new TextButton("Bad Zaat", skin);
        track3Button = new TextButton("Extasy", skin);
        track4Button = new TextButton("Ghors", skin);
        track5Button = new TextButton("Lonely", skin);
        track6Button = new TextButton("Sweet Dreams", skin);
        track7Button = new TextButton("Tabestoon Kootahe", skin);
        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(track1Button).size(250, 50).pad(10);
        table.row();
        table.add(track2Button).size(250, 50).pad(10);
        table.row();
        table.add(track3Button).size(250, 50).pad(10);
        table.row();
        table.add(track4Button).size(250, 50).pad(10);
        table.row();
        table.add(track5Button).size(250, 50).pad(10);
        table.row();
        table.add(track6Button).size(250, 50).pad(10);
        table.row();
        table.add(track7Button).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);
        return table;
    }

    private void updateSelection() {
        track1Button.setColor(index == 0 ? Color.RED : Color.WHITE);
        track2Button.setColor(index == 1 ? Color.RED : Color.WHITE);
        track3Button.setColor(index == 2 ? Color.RED : Color.WHITE);
        track4Button.setColor(index == 3 ? Color.RED : Color.WHITE);
        track5Button.setColor(index == 4 ? Color.RED : Color.WHITE);
        track6Button.setColor(index == 5 ? Color.RED : Color.WHITE);
        track7Button.setColor(index == 6 ? Color.RED : Color.WHITE);
        backButton.setColor(index == 7 ? Color.RED : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            index = (index + 1) % 8;
        } else if (keycode == Input.Keys.UP) {
            index = (index - 1 + 8) % 8;
        } else if (keycode == Input.Keys.ENTER) {
            if (index == 0) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\akharin.mp3");
            } else if (index == 1) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\bad.mp3");
            } else if (index == 2) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\extasy.mp3");
            } else if (index == 3) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\ghors.mp3");
            } else if (index == 4) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\lonely.mp3");
            } else if (index == 5) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\sweet.mp3");
            } else if (index == 6) {
                MusicManager.changeMusic("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\tabestoon.mp3");
            } else if (index == 7) {
                ChangeMenuController.changeMenu(MusicMenu.this, new MainMenu());
            }
            updateSelection();
            return true;
        }
        updateSelection();
        return false;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

