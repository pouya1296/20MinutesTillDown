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
import org.example.controller.DatabaseManager;
import org.example.controller.LoginMeuController;

import java.sql.SQLException;

public class LoginMenu implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextField usernameField, passwordField, answerField;
    private Label messageLabel;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\untitled\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        createFields();
        Table table = createTable();
        createStage(table);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void createStage(Table table) {
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\untitled\\src\\main\\java\\org\\example\\view\\project assets\\loginMenu.jpeg"));
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
        table.add(passwordField).width(300).padBottom(20);
        table.row();
        table.add(answerField).width(300).padBottom(20);
        return table;
    }

    private void createFields() {
        messageLabel = new Label(Language.ENTER_YOUR_USERNAME.languages[App.languageNumber], skin);
        messageLabel.setFontScale(2);
        usernameField = new TextField("", skin);
        usernameField.setAlignment(Align.center);
        passwordField = new TextField("", skin);
        passwordField.setMessageText(Language.TYPE_YOUR_PASSWORD.languages[App.languageNumber]);
        passwordField.setVisible(false);
        passwordField.setAlignment(Align.center);
        answerField = new TextField("", skin);
        answerField.setMessageText(Language.ANSWER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
        answerField.setVisible(false);
        answerField.setAlignment(Align.center);
        usernameField.setTouchable(Touchable.enabled);
        passwordField.setTouchable(Touchable.enabled);
        answerField.setTouchable(Touchable.enabled);
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
                    usernameResponse = LoginMeuController.checkUser(username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (!usernameResponse.success) {
                    messageLabel.setText(usernameResponse.responseMessage);
                } else {
                    messageLabel.setText(Language.TYPE_YOUR_PASSWORD_OR_FORGET.languages[App.languageNumber]);
                    usernameField.setVisible(false);
                    passwordField.setVisible(true);
                    stage.setKeyboardFocus(passwordField);
                    index = 1;
                }
            } else if (index == 1) {
                String password = passwordField.getText();
                if (password.equals("forget")) {
                    messageLabel.setText(Language.ANSWER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
                    passwordField.setVisible(false);
                    answerField.setVisible(true);
                    stage.setKeyboardFocus(answerField);
                    index = 2;
                } else {
                    Response passwordResponse = null;
                    try {
                        passwordResponse = LoginMeuController.login(usernameField.getText(), password);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (!passwordResponse.success) {
                        messageLabel.setText(passwordResponse.responseMessage);
                    } else {
                        ;
                        messageLabel.setText(Language.LOGGED_IN_SUCCESSFULLY.languages[App.languageNumber]);
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }
                }
            } else if (index == 2) {
                String question = answerField.getText();
                if (question.isEmpty()) {
                    messageLabel.setText(Language.ENTER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
                } else {
                    messageLabel.setText(Language.LOGGED_IN_SUCCESSFULLY.languages[App.languageNumber]);
                    try {
                        App.currentUser = DatabaseManager.getUser(usernameField.getText());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
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
