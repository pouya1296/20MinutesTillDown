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
import org.example.Model.User;
import org.example.controller.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class ScoreboardMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton sortByScoreButton, sortByUsernameButton, sortByKillsButton, sortBySurvivalTimeButton, backButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        createScoreboardButtons();
        Table table = createTable();
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\messi.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
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
        sortByScoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    sortUsersByScore();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        sortByUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    sortUsersByUsername();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        sortByKillsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    sortUsersByKills();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        sortBySurvivalTimeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    sortUsersBySurvivalTime();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
    }


    private void createScoreboardButtons() {
        sortByScoreButton = new TextButton("Sort by Score", skin);
        sortByUsernameButton = new TextButton("Sort by Username", skin);
        sortByKillsButton = new TextButton("Sort by Kills", skin);
        sortBySurvivalTimeButton = new TextButton("Sort by Survival Time", skin);
        backButton = new TextButton("Back to Main Menu", skin);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(sortByScoreButton).size(250, 50).pad(10);
        table.row();
        table.add(sortByUsernameButton).size(250, 50).pad(10);
        table.row();
        table.add(sortByKillsButton).size(250, 50).pad(10);
        table.row();
        table.add(sortBySurvivalTimeButton).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);
        return table;
    }

    private void updateSelection() {
        sortByScoreButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        sortByUsernameButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        sortByKillsButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        sortBySurvivalTimeButton.setColor(index == 3 ? Color.RED : Color.WHITE);
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
                try {
                    sortUsersByScore();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (index == 1) {
                try {
                    sortUsersByUsername();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (index == 2) {
                try {
                    sortUsersByKills();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (index == 3) {
                try {
                    sortUsersBySurvivalTime();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (index == 4) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
            updateSelection();
            return true;
        }
        updateSelection();
        return false;
    }

    private void sortUsersByScore() throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));
        ((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreboardDisplay(users));
    }

    private void sortUsersByUsername() throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));
        ((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreboardDisplay(users));
    }

    private void sortUsersByKills() throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getTotalKills(), u1.getTotalKills()));
        ((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreboardDisplay(users));
    }

    private void sortUsersBySurvivalTime() throws SQLException {
        List<User> users = DatabaseManager.getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getSurvivalTime(), u1.getSurvivalTime()));
        ((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreboardDisplay(users));
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
    public void pause() {}

    @Override
    public void resume() {}

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