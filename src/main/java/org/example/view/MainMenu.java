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
import org.example.controller.DatabaseManager;

import java.sql.SQLException;

public class MainMenu extends Game implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton preGameButton, settingsButton, profileButton, scoreboardButton, hintButton, continueButton, exitButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Image avatarImage;
    private Texture avatarTexture;
    private Label usernameLabel, scoreLabel;

    @Override
    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\mainMenu.jpeg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        avatarTexture = new Texture(Gdx.files.internal(App.currentUser.avatarPath));
        avatarImage = new Image(avatarTexture);
        usernameLabel = new Label(App.currentUser.username, skin);
        scoreLabel = new Label(String.valueOf(App.currentUser.score), skin);
        createMainMenuButtons();
        Table table = createTable();
        Table userInfoTable = createUserInfoTable();
        stage.addActor(backgroundImage);
        stage.addActor(userInfoTable);
        stage.addActor(table);
        updateSelection();
        addClickListeners();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void addClickListeners() {
        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreGameMenu());
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsMenu());
            }
        });

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ProfileMenu());
            }
        });

        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreboardMenu());
            }
        });

        hintButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HintMenu());
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ContinueGameMenu());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    DatabaseManager.updateUser(App.currentUser);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                App.currentUser = null;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new StartMenu());
            }
        });
    }

    private Table createUserInfoTable() {
        Table userInfoTable = new Table();
        userInfoTable.top().left();
        userInfoTable.setFillParent(true);
        userInfoTable.padTop(50);
        userInfoTable.add(avatarImage).size(90, 90).pad(10);
        userInfoTable.add(usernameLabel).pad(10);
        userInfoTable.add(scoreLabel).pad(10);
        usernameLabel.setFontScale(3);
        usernameLabel.setFontScale(3);
        return userInfoTable;
    }


    private void createMainMenuButtons() {
        preGameButton = new TextButton("Pre Game Menu", skin);
        settingsButton = new TextButton("Settings", skin);
        profileButton = new TextButton("Profile", skin);
        scoreboardButton = new TextButton("Scoreboard", skin);
        hintButton = new TextButton("Hint Menu", skin);
        continueButton = new TextButton("Continue Saved Game", skin);
        exitButton = new TextButton("Logout", skin);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(preGameButton).size(250, 50).pad(10);
        table.row();
        table.add(settingsButton).size(250, 50).pad(10);
        table.row();
        table.add(profileButton).size(250, 50).pad(10);
        table.row();
        table.add(scoreboardButton).size(250, 50).pad(10);
        table.row();
        table.add(hintButton).size(250, 50).pad(10);
        table.row();
        table.add(continueButton).size(250, 50).pad(10);
        table.row();
        table.add(exitButton).size(250, 50).pad(10);
        return table;
    }

    private void updateSelection() {
        preGameButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        settingsButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        profileButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        scoreboardButton.setColor(index == 3 ? Color.RED : Color.WHITE);
        hintButton.setColor(index == 4 ? Color.RED : Color.WHITE);
        continueButton.setColor(index == 5 ? Color.RED : Color.WHITE);
        exitButton.setColor(index == 6 ? Color.RED : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            index = (index + 1) % 7;
        } else if (keycode == Input.Keys.UP) {
            index = (index - 1 + 7) % 7;
        } else if (keycode == Input.Keys.ENTER) {
            if (index == 0) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreGameMenu());
            } else if (index == 1) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsMenu());
            } else if (index == 2) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ProfileMenu());
            } else if (index == 3) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreboardMenu());
            } else if (index == 4) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HintMenu());
            } else if (index == 5) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ContinueGameMenu());
            } else {
                try {
                    DatabaseManager.updateUser(App.currentUser);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                App.currentUser = null;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new StartMenu());
            }
        }
        updateSelection();
        return true;
    }

    @Override
    public void show() {
        handleMenu();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void create() {
        setScreen(this);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
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

