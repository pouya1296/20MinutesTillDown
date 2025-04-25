package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.Language;
import org.example.Model.Response;
import org.example.Model.User;
import org.example.controller.ChangeUsernameController;
import org.example.controller.DatabaseManager;
import org.example.controller.GameDataManager;
import org.example.controller.RegisterMenuController;

import java.sql.SQLException;

public class ChangeUsername implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextField usernameField;
    private Label messageLabel;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        createFields();
        Table table = createTable();
        createStage(table);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void createStage(Table table) {
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\mainMenu.jpeg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(table);
        stage.setKeyboardFocus(usernameField);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(messageLabel).colspan(2).padBottom(20);
        table.row();
        table.add(usernameField).width(300).padBottom(20);
        table.row();
        return table;
    }

    private void createFields() {
        messageLabel = new Label(Language.ENTER_YOUR_USERNAME.languages[App.languageNumber], skin);
        messageLabel.setFontScale(2);
        usernameField = new TextField("", skin);
        usernameField.setAlignment(Align.center);
        usernameField.setTouchable(Touchable.enabled);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new StartMenu());
        if (keycode == Input.Keys.ENTER) {
            if (index == 0) {
                String username = usernameField.getText();
                Response usernameResponse;
                try {
                    usernameResponse = ChangeUsernameController.checkUsername(username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (!usernameResponse.success) {
                    messageLabel.setText(usernameResponse.responseMessage);
                } else {
                    messageLabel.setText("username changed to: " + username);
                    try {
                        DatabaseManager.updateUsername(App.currentUser.username, username);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    GameDataManager.updateGameUsername(App.currentUser.username, username);
                    App.currentUser.username = username;
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ProfileMenu());
                }
            }
        }
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
        handleMenu();
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
        stage.dispose();
        skin.dispose();
    }
}

