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

public class PreGameMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton heroMenuButton, weaponMenuButton, timeMenuButton, newGameButton, backButton;
    private int index = 0;
    private Texture backgroundTexture;
    private Image backgroundImage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\PreGame.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        heroMenuButton = new TextButton(Language.HERO_MENU.languages[App.languageNumber], skin);
        weaponMenuButton = new TextButton(Language.WEAPON_MENU.languages[App.languageNumber], skin);
        timeMenuButton = new TextButton(Language.TIMER_MENU.languages[App.languageNumber], skin);
        newGameButton = new TextButton(Language.NEW_GAME.languages[App.languageNumber], skin);
        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);

        addClick();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(heroMenuButton).size(400, 100).pad(10);
        table.row();
        table.add(weaponMenuButton).size(400, 100).pad(10);
        table.row();
        table.add(timeMenuButton).size(400, 100).pad(10);
        table.row();
        table.add(newGameButton).size(400, 100).pad(10);
        table.row();
        table.add(backButton).size(400, 100).pad(10);

        stage.addActor(table);
        updateSelection();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void addClick() {
        heroMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(PreGameMenu.this, new HeroMenu());
            }
        });

        weaponMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(PreGameMenu.this, new WeaponMenu());
            }
        });

        timeMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(PreGameMenu.this, new TimeMenu());
            }
        });

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentGame = new org.example.Model.Game(App.currentUser);
                ChangeMenuController.changeMenu(PreGameMenu.this, new GameMenu(App.currentGame));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(PreGameMenu.this, new MainMenu());
            }
        });
    }

    private void updateSelection() {
        heroMenuButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        weaponMenuButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        timeMenuButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        newGameButton.setColor(index == 3 ? Color.RED : Color.WHITE);
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
                ChangeMenuController.changeMenu(this, new HeroMenu());
            } else if (index == 1) {
                ChangeMenuController.changeMenu(this, new WeaponMenu());
            } else if (index == 2) {
                ChangeMenuController.changeMenu(this, new TimeMenu());
            } else if (index == 3) {
                App.currentGame = new org.example.Model.Game(App.currentUser);
                ChangeMenuController.changeMenu(this, App.currentGame.gameMenu);
            } else {
                ChangeMenuController.changeMenu(this, new MainMenu());
            }
        }
        updateSelection();
        return true;
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
        stage.clear();
        stage.dispose();
        skin.dispose();
    }
}
