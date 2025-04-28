package org.example.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.enums.Language;
import org.example.controller.ChangeMenuController;
import org.example.controller.MusicManager;

public class SettingsMenu implements Menu, Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private TextButton musicOnOffButton, musicTrackButton, soundEffectButton, keyboardControlButton, autoReloadButton, grayscaleButton, backButton;
    private int index = 0;
    private CheckBox soundEffectCheckBox, autoReloadCheckBox, grayscaleCheckBox;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton volumeButton;
    private Slider volumeSlider;

    @Override
    public void show() {
        handleMenu();
    }

    private void showBooleanVariables() {
        soundEffectCheckBox = new CheckBox(Language.SOUND_EFFECTS.languages[App.languageNumber], skin);
        autoReloadCheckBox = new CheckBox(Language.AUTO_RELOAD.languages[App.languageNumber], skin);
        grayscaleCheckBox = new CheckBox(Language.GRAYSCALE_MODE.languages[App.languageNumber], skin);
        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
        soundEffectCheckBox.setChecked(App.currentUser.soundEffect);
        autoReloadCheckBox.setChecked(App.currentUser.autoReload);
        grayscaleCheckBox.setChecked(App.currentUser.grayScaleMode);
        soundEffectCheckBox.addListener(event -> {
            App.currentUser.soundEffect = soundEffectCheckBox.isChecked();
            return true;
        });
        autoReloadCheckBox.addListener(event -> {
            App.currentUser.autoReload = autoReloadCheckBox.isChecked();
            return true;
        });
        grayscaleCheckBox.addListener(event -> {
            App.currentUser.grayScaleMode = grayscaleCheckBox.isChecked();
            return true;
        });
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(MusicManager.getBackgroundMusic().getVolume());
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MusicManager.setVolume(volumeSlider.getValue());
            }
        });

    }

    private void createSettingsButtons() {
        musicOnOffButton = new TextButton(Language.MUSIC_ON_OFF.languages[App.languageNumber], skin);
        musicTrackButton = new TextButton(Language.MUSIC_TRACK_SELECTION.languages[App.languageNumber], skin);
        soundEffectButton = new TextButton(Language.SOUND_EFFECT_TOGGLE.languages[App.languageNumber], skin);
        keyboardControlButton = new TextButton(Language.KEYBOARD_CONTROLS.languages[App.languageNumber], skin);
        autoReloadButton = new TextButton(Language.AUTO_RELOAD_SETTING.languages[App.languageNumber], skin);
        grayscaleButton = new TextButton(Language.GRAYSCALE_MODE.languages[App.languageNumber], skin);
        volumeButton = new TextButton(Language.CHANGE_VOLUME.languages[App.languageNumber], skin);
        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(soundEffectCheckBox).size(250, 50).pad(10);
        table.row();
        table.add(autoReloadCheckBox).size(250, 50).pad(10);
        table.row();
        table.add(grayscaleCheckBox).size(250, 50).pad(10);
        table.row();
        table.add(musicOnOffButton).size(250, 50).pad(10);
        table.row();
        table.add(musicTrackButton).size(250, 50).pad(10);
        table.row();
        table.add(soundEffectButton).size(250, 50).pad(10);
        table.row();
        table.add(keyboardControlButton).size(250, 50).pad(10);
        table.row();
        table.add(autoReloadButton).size(250, 50).pad(10);
        table.row();
        table.add(grayscaleButton).size(250, 50).pad(10);
        table.row();
        table.add(volumeButton).size(250, 50).pad(10);
        table.row();
        table.add(volumeSlider).size(250, 50).pad(10);
        table.row();
        table.add(backButton).size(250, 50).pad(10);
        table.row();

        return table;
    }

    private void updateSelection() {
        musicOnOffButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        musicTrackButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        soundEffectButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        keyboardControlButton.setColor(index == 3 ? Color.RED : Color.WHITE);
        autoReloadButton.setColor(index == 4 ? Color.RED : Color.WHITE);
        grayscaleButton.setColor(index == 5 ? Color.RED : Color.WHITE);
        volumeButton.setColor(index == 6 ? Color.RED : Color.WHITE);
        backButton.setColor(index == 7 ? Color.RED : Color.WHITE);

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            index = (index + 1) % 8;
        } else if (keycode == Input.Keys.UP) {
            index = (index - 1 + 8) % 8;
        } else if (keycode == Input.Keys.ENTER) {
            if (index == 0) {
                if (MusicManager.getBackgroundMusic().isPlaying())
                    MusicManager.stop();
                else
                    MusicManager.play();
            } else if (index == 1) {
                ChangeMenuController.changeMenu(SettingsMenu.this, new MusicMenu());
            } else if (index == 2) {
                App.currentUser.soundEffect = !App.currentUser.soundEffect;
                soundEffectCheckBox.setChecked(App.currentUser.soundEffect);
            } else if (index == 3) {
                ChangeMenuController.changeMenu(SettingsMenu.this, new KeyboardMenu(App.currentUser.keys));
            } else if (index == 4) {
                App.currentUser.autoReload = !App.currentUser.autoReload;
                autoReloadCheckBox.setChecked(App.currentUser.autoReload);
            } else if (index == 5) {
                App.currentUser.grayScaleMode = !App.currentUser.grayScaleMode;
                grayscaleCheckBox.setChecked(App.currentUser.grayScaleMode);
            } else if (index == 7) {
                ChangeMenuController.changeMenu(SettingsMenu.this, new MainMenu());
            }
            updateSelection();
            return true;
        }
        if (index == 6) {
            if (keycode == Input.Keys.LEFT) {
                float newVolume = Math.max(0f, volumeSlider.getValue() - 0.05f);
                volumeSlider.setValue(newVolume);
                MusicManager.setVolume(newVolume);
                return true;
            } else if (keycode == Input.Keys.RIGHT) {
                float newVolume = Math.min(1f, volumeSlider.getValue() + 0.05f);
                volumeSlider.setValue(newVolume);
                MusicManager.setVolume(newVolume);
                return true;
            }
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

    @Override
    public void handleMenu() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\settingMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        createSettingsButtons();
        showBooleanVariables();
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
        musicOnOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MusicManager.getBackgroundMusic().isPlaying()) {
                    MusicManager.stop();
                } else {
                    MusicManager.play();
                }
            }
        });

        musicTrackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(SettingsMenu.this, new MusicMenu());
            }
        });

        soundEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 2;
                App.currentUser.soundEffect = !App.currentUser.soundEffect;
                soundEffectCheckBox.setChecked(App.currentUser.soundEffect);
                updateSelection();
            }
        });

        keyboardControlButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(SettingsMenu.this, new KeyboardMenu(App.currentUser.keys));
            }
        });

        autoReloadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 4;
                App.currentUser.autoReload = !App.currentUser.autoReload;
                autoReloadCheckBox.setChecked(App.currentUser.autoReload);
                updateSelection();
            }
        });

        grayscaleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 5;
                App.currentUser.grayScaleMode = !App.currentUser.grayScaleMode;
                grayscaleCheckBox.setChecked(App.currentUser.grayScaleMode);
                updateSelection();
            }
        });

        volumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 6;
                float newVolume = volumeSlider.getValue();
                MusicManager.setVolume(newVolume);
                updateSelection();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(SettingsMenu.this, new MainMenu());
            }
        });
    }

}
