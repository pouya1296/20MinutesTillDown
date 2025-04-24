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
import org.example.controller.DatabaseManager;
import org.example.controller.RegisterMenuController;

import java.sql.SQLException;

public class RegisterMenu implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextField usernameField, passwordField, questionField, answerField;
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
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\registerMenu.png"));
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
        table.add(questionField).width(300).padBottom(20);
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
        questionField = new TextField("", skin);
        questionField.setMessageText(Language.ENTER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
        questionField.setVisible(false);
        questionField.setAlignment(Align.center);
        answerField = new TextField("", skin);
        answerField.setMessageText(Language.ANSWER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
        answerField.setVisible(false);
        answerField.setAlignment(Align.center);
        usernameField.setTouchable(Touchable.enabled);
        passwordField.setTouchable(Touchable.enabled);
        questionField.setTouchable(Touchable.enabled);
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
                    usernameResponse = RegisterMenuController.checkUsername(username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (!usernameResponse.success) {
                    messageLabel.setText(usernameResponse.responseMessage);
                } else {
                    messageLabel.setText(Language.TYPE_YOUR_PASSWORD.languages[App.languageNumber]);
                    usernameField.setVisible(false);
                    passwordField.setVisible(true);
                    stage.setKeyboardFocus(passwordField);
                    index = 1;
                }
            } else if (index == 1) {
                String password = passwordField.getText();
                Response passwordResponse = RegisterMenuController.checkPassword(password);
                if (!passwordResponse.success) {
                    messageLabel.setText(passwordResponse.responseMessage);
                } else {
                    messageLabel.setText(Language.ENTER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
                    passwordField.setVisible(false);
                    questionField.setVisible(true);
                    stage.setKeyboardFocus(questionField);
                    index = 2;
                }
            } else if (index == 2) {
                String question = questionField.getText();
                if (question.isEmpty()) {
                    messageLabel.setText(Language.ENTER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
                } else {
                    messageLabel.setText(Language.ANSWER_YOUR_SECURITY_QUESTION.languages[App.languageNumber]);
                    questionField.setVisible(false);
                    answerField.setVisible(true);
                    stage.setKeyboardFocus(answerField);
                    index = 3;
                }
            } else if (index == 3) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String question = questionField.getText();
                String answer = answerField.getText();
                User user = new User(username, password, question, answer, App.getAvatarPath(), 0, 0, 0);
                try {
                    DatabaseManager.saveUser(user);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                messageLabel.setText(Language.REGISTERED_SUCCESSFULLY.languages[App.languageNumber]);
                App.currentUser = user;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
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
