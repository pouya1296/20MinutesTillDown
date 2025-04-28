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
import org.example.Model.Response;
import org.example.Model.enums.Language;
import org.example.controller.ChangeMenuController;
import org.example.controller.DatabaseManager;
import org.example.controller.RegisterMenuController;

import java.sql.SQLException;

public class ChangePassword implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextField passwordField;
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
        stage.setKeyboardFocus(passwordField);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(messageLabel).colspan(2).padBottom(20);
        table.row();
        table.add(passwordField).width(300).padBottom(20);
        table.row();
        return table;
    }

    private void createFields() {
        messageLabel = new Label(Language.TYPE_YOUR_PASSWORD.languages[App.languageNumber], skin);
        messageLabel.setFontScale(2);
        passwordField = new TextField("", skin);
        passwordField.setAlignment(Align.center);
        passwordField.setTouchable(Touchable.enabled);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            ChangeMenuController.changeMenu(ChangePassword.this, new ProfileMenu());
        if (keycode == Input.Keys.ENTER) {
            if (index == 0) {
                String password = passwordField.getText();
                Response passwordResponse = RegisterMenuController.checkPassword(password);
                if (!passwordResponse.success) {
                    messageLabel.setText(passwordResponse.responseMessage);
                } else {
                    messageLabel.setText(Language.PASS_CHANGE.languages[App.languageNumber] + password);
                    App.currentUser.password = password;
                    try {
                        DatabaseManager.updateUser(App.currentUser);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ChangeMenuController.changeMenu(ChangePassword.this, new ProfileMenu());
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

