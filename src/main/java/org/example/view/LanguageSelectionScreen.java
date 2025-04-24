package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;

public class LanguageSelectionScreen extends  Game implements Screen, InputProcessor {

    private Stage stage;
    private Skin skin;
    private TextButton englishButton, frenchButton;
    private int selectedIndex = 0;

    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        Label titleLabel = new Label("Select Language", skin);
        titleLabel.setFontScale(2);
        titleLabel.setAlignment(Align.center);
        Table table = createButtons(titleLabel);
        stage.addActor(table);
        Gdx.input.setInputProcessor(this);
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
        table.add(englishButton).size(200, 50).pad(10);
        table.row();
        table.add(frenchButton).size(200, 50).pad(10);
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
            ((Game) Gdx.app.getApplicationListener()).setScreen(new StartMenu());
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
