package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.User;
import org.example.Model.enums.Language;
import org.example.controller.ChangeMenuController;


public class StartMenu extends Game implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton registerButton, loginButton, guestButton, exitButton;
    private int selectedIndex = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    @Override
    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\registerbackground.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        registerButton = new TextButton(Language.REGISTER.languages[App.languageNumber], skin);
        loginButton = new TextButton(Language.LOGIN.languages[App.languageNumber], skin);
        guestButton = new TextButton(Language.PLAY_AS_GUEST.languages[App.languageNumber], skin);
        exitButton = new TextButton(Language.EXIT.languages[App.languageNumber], skin);
        Table messageTable = writeMyName();
        Table table = createButtons();
        addStageActors(table, messageTable);
        updateSelection();
        addClickListener();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void addClickListener() {
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(StartMenu.this, new RegisterMenu());
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(StartMenu.this, new LoginMenu());
            }
        });

        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentUser = new User("guest", " ", " ", " ", App.getAvatarPath(), 0, 0, 0, true);
                App.currentGame  = new org.example.Model.Game(App.currentUser);
                ChangeMenuController.changeMenu(StartMenu.this, new GameMenu(App.currentGame));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
    }


    private void addStageActors(Table table, Table messageTable) {
        stage.addActor(backgroundImage);
        stage.addActor(table);
        stage.addActor(messageTable);
    }

    private Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(registerButton).size(400, 100).pad(10);
        table.row();
        table.add(loginButton).size(400, 100).pad(10);
        table.row();
        table.add(guestButton).size(400, 100).pad(10);
        table.row();
        table.add(exitButton).size(400, 100).pad(10);
        return table;
    }

    private Table writeMyName() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("font");
        labelStyle.fontColor = Color.RED;
        Label bottomMessage = new Label("Pouya Astaraki", labelStyle);
        bottomMessage.setFontScale(2);
        bottomMessage.setAlignment(Align.center);
        Table messageTable = new Table();
        messageTable.setFillParent(true);
        messageTable.bottom();
        messageTable.add(bottomMessage).expandX().center().padBottom(50);
        return messageTable;
    }

    private void updateSelection() {
        registerButton.setColor(selectedIndex == 0 ? Color.RED : Color.WHITE);
        loginButton.setColor(selectedIndex == 1 ? Color.RED : Color.WHITE);
        guestButton.setColor(selectedIndex == 2 ? Color.RED : Color.WHITE);
        exitButton.setColor(selectedIndex == 3 ? Color.RED : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            selectedIndex = (selectedIndex + 1) % 4;
        } else if (keycode == Input.Keys.UP) {
            selectedIndex = (selectedIndex - 1 + 4) % 4;
        } else if (keycode == Input.Keys.ENTER) {
            if (selectedIndex == 0) {
                ChangeMenuController.changeMenu(StartMenu.this, new RegisterMenu());
            } else if (selectedIndex == 1) {
                ChangeMenuController.changeMenu(StartMenu.this, new LoginMenu());
            } else if (selectedIndex == 2) {
                App.currentUser = new User("guest", " ", " ", " ", App.getAvatarPath(), 0, 0, 0, true);
                App.currentGame = new org.example.Model.Game(App.currentUser);
                ChangeMenuController.changeMenu(StartMenu.this, App.currentGame.gameMenu);
            } else {
                System.exit(0);
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
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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