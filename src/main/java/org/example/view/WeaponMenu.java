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
import org.example.Model.App;
import org.example.Model.enums.Language;
import org.example.Model.enums.Weapon;
import org.example.controller.ChangeMenuController;

public class WeaponMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private Label selectedWeaponLabel;
    private TextButton revolverButton, shotgunButton, smgDualButton, backButton;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private int index = 0;
    private Weapon[] weapons = {Weapon.REVOLVER, Weapon.SHOTGUN, Weapon.SMG_S_DUAL};

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\PreGame.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        selectedWeaponLabel = new Label(Language.SELECTED_WEAPON.languages[App.languageNumber] + App.currentUser.weapon, skin);
        selectedWeaponLabel.setColor(Color.YELLOW);

        revolverButton = new TextButton("REVOLVER", skin);
        shotgunButton = new TextButton("SHOTGUN", skin);
        smgDualButton = new TextButton("SMGs DUAL", skin);
        backButton = new TextButton(Language.BACK_TO_PRE_GAME_MENU.languages[App.languageNumber], skin);

        addClickListeners();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(selectedWeaponLabel).pad(10);
        table.row();
        table.add(revolverButton).size(250, 50).pad(10);
        table.row();
        table.add(shotgunButton).size(250, 50).pad(10);
        table.row();
        table.add(smgDualButton).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);

        stage.addActor(table);
        updateSelection();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void addClickListeners() {
        ClickListener revolverClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 0;
                App.currentUser.weapon = weapons[index];
                selectedWeaponLabel.setText(Language.SELECTED_WEAPON.languages[App.languageNumber] + App.currentUser.weapon.name);
                updateSelection();
            }
        };
        ClickListener shotgunClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 1;
                App.currentUser.weapon = weapons[index];
                selectedWeaponLabel.setText(Language.SELECTED_WEAPON.languages[App.languageNumber] + App.currentUser.weapon.name);
                updateSelection();
            }
        };
        ClickListener smgClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 2;
                App.currentUser.weapon = weapons[index];
                selectedWeaponLabel.setText(Language.SELECTED_WEAPON.languages[App.languageNumber] + App.currentUser.weapon.name);
                updateSelection();
            }
        };

        revolverButton.addListener(revolverClickListener);
        shotgunButton.addListener(shotgunClickListener);
        smgDualButton.addListener(smgClickListener);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(WeaponMenu.this, new PreGameMenu());
            }
        });
    }

    private void updateSelection() {
        revolverButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        shotgunButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        smgDualButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        backButton.setColor(index == 3 ? Color.RED : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            index = (index + 1) % (weapons.length + 1);
        } else if (keycode == Input.Keys.UP) {
            index = (index - 1 + (weapons.length + 1)) % (weapons.length + 1);
        } else if (keycode == Input.Keys.ENTER) {
            if (index == 3) {
                ChangeMenuController.changeMenu(WeaponMenu.this, new PreGameMenu());
            } else {
                App.currentUser.weapon = weapons[index];
                selectedWeaponLabel.setText(Language.SELECTED_WEAPON.languages[App.languageNumber] + weapons[index].name);
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
    public void resize(int width, int height) {
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
    }
}
