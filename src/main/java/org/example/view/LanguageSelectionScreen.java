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
import org.example.controller.ChangeMenuController;
import org.example.controller.MusicManager;

public class LanguageSelectionScreen extends Game implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton englishButton, frenchButton;
    private int selectedIndex = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        Label titleLabel = new Label("Select Language", skin);
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\registerbackground.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        titleLabel.setFontScale(1.5f);
        titleLabel.setAlignment(Align.center);
        Table table = createButtons(titleLabel);
        stage.addActor(table);
        addClickListener();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void addClickListener() {
        englishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.languageNumber = 0;
                MusicManager.initialize();
                MusicManager.play();
                ChangeMenuController.changeMenu(LanguageSelectionScreen.this, new StartMenu());
            }
        });

        frenchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.languageNumber = 1;
                MusicManager.initialize();
                MusicManager.play();
                ChangeMenuController.changeMenu(LanguageSelectionScreen.this, new StartMenu());
            }
        });
    }


    private Table createButtons(Label titleLabel) {
        englishButton = new TextButton("English", skin);
        frenchButton = new TextButton("Français", skin);
        updateSelection();
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(titleLabel).colspan(2).padBottom(20);
        table.row();
        table.add(englishButton).size(400, 100).pad(10);
        table.row();
        table.add(frenchButton).size(400, 100).pad(10);
        return table;
    }

    private void updateSelection() {
        englishButton.setColor(selectedIndex == 0 ? Color.YELLOW : Color.WHITE);
        frenchButton.setColor(selectedIndex == 1 ? Color.YELLOW : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.UP) {
            selectedIndex = (selectedIndex + 1) % 2;
            updateSelection();
        } else if (keycode == Input.Keys.ENTER) {
            if (selectedIndex == 0) {
                App.languageNumber = 0;
            } else {
                App.languageNumber = 1;
            }
            MusicManager.initialize();
            MusicManager.play();
            ChangeMenuController.changeMenu(LanguageSelectionScreen.this, new StartMenu());
        }
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
