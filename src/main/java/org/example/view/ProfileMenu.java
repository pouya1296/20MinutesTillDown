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
import org.example.controller.DatabaseManager;

import java.sql.SQLException;

public class ProfileMenu implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton changeUsernameButton, changePasswordButton, deleteAccountButton, changeAvatarButton, backButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;


    @Override
    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\settingMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        createProfileButtons();
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
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new ChangeUsername());
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new ChangePassword());
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    DatabaseManager.deleteUser(App.currentUser.username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                App.currentUser = null;
                ChangeMenuController.changeMenu(ProfileMenu.this, new StartMenu());
            }
        });

        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new AvatarMenu());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new MainMenu());
            }
        });
    }


    @Override
    public void show() {
        handleMenu();
    }

    private void createProfileButtons() {
        changeUsernameButton = new TextButton(Language.CHANGE_USERNAME.languages[App.languageNumber], skin);
        changePasswordButton = new TextButton(Language.CHANGE_PASSWORD.languages[App.languageNumber], skin);
        deleteAccountButton = new TextButton(Language.DELETE_ACCOUNT.languages[App.languageNumber], skin);
        changeAvatarButton = new TextButton(Language.CHANGE_AVATAR.languages[App.languageNumber], skin);
        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(changeUsernameButton).size(250, 50).pad(10);
        table.row();
        table.add(changePasswordButton).size(250, 50).pad(10);
        table.row();
        table.add(deleteAccountButton).size(250, 50).pad(10);
        table.row();
        table.add(changeAvatarButton).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);
        return table;
    }

    private void updateSelection() {
        changeUsernameButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        changePasswordButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        deleteAccountButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        changeAvatarButton.setColor(index == 3 ? Color.RED : Color.WHITE);
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
                ChangeMenuController.changeMenu(ProfileMenu.this, new ChangeUsername());
            } else if (index == 1) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new ChangePassword());
            } else if (index == 2) {
                try {
                    DatabaseManager.deleteUser(App.currentUser.username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                App.currentUser = null;
                ChangeMenuController.changeMenu(ProfileMenu.this, new StartMenu());
            } else if (index == 3) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new AvatarMenu());
            } else if (index == 4) {
                ChangeMenuController.changeMenu(ProfileMenu.this, new MainMenu());
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
