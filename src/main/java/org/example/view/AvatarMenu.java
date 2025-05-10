package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import org.example.controller.FilePicker;

public class AvatarMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Image[] avatarImages;
    private Texture[] avatarTextures;
    private TextButton selectFileButton, backButton;
    private int selectedIndex = -1;
    private Texture backgroundTexture, currentAvatarTexture;
    private Image backgroundImage, currentAvatarImage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\settingMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        String[] avatarPaths = new String[1000];

        avatarTextures = new Texture[7];
        avatarImages = new Image[7];

        for (int i = 0; i < 7; i++) {
            avatarTextures[i] = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\avatars\\avatar" + (i + 1) + ".jpg"));
            avatarImages[i] = new Image(avatarTextures[i]);
            avatarPaths[i] = "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\avatars\\avatar" + (i + 1) + ".jpg";
            final int index = i;
            avatarImages[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedIndex = index;
                    updateSelection();
                    App.currentUser.avatarPath = avatarPaths[index];
                    currentAvatarTexture = avatarTextures[index];
                }
            });
        }

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        Table avatarTable = new Table();
        avatarTable.center();
        for (Image avatar : avatarImages) {
            avatarTable.add(avatar).size(100, 100).pad(5);
        }

        selectFileButton = new TextButton(Language.SELECT_FROM_FILE.languages[App.languageNumber], skin);
        backButton = new TextButton(Language.BACK_TO_PROFILE.languages[App.languageNumber], skin);

        selectFileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selectedAvatarPath = FilePicker.selectFile();
                if (selectedAvatarPath != null) {
                    App.currentUser.avatarPath = selectedAvatarPath;
                    currentAvatarTexture = new Texture(Gdx.files.internal(selectedAvatarPath));
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(AvatarMenu.this, new ProfileMenu());
            }
        });
        currentAvatarTexture = new Texture(Gdx.files.internal(App.currentUser.avatarPath));
        currentAvatarImage = new Image(currentAvatarTexture);
        currentAvatarImage.setSize(150, 150);
        Table buttonTable = new Table();
        buttonTable.center();
        buttonTable.add(selectFileButton).size(250, 50).pad(10);
        buttonTable.row();
        buttonTable.add(backButton).size(250, 50).pad(10);

        mainTable.add(currentAvatarImage).size(150, 150).padBottom(20);
        mainTable.row();
        mainTable.add(avatarTable);
        mainTable.row();
        mainTable.add(buttonTable);

        stage.addActor(mainTable);
    }

    private void updateSelection() {
        for (int i = 0; i < avatarImages.length; i++) {
            avatarImages[i].setColor(i == selectedIndex ? Color.RED : Color.WHITE);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        currentAvatarTexture.dispose();
        for (Texture texture : avatarTextures) {
            texture.dispose();
        }
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
}
