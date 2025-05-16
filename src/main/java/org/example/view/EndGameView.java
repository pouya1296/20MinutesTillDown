package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.Game;
import org.example.Model.enums.Ability;
import org.example.Model.enums.Hero;
import org.example.Model.enums.Language;
import org.example.Model.enums.Weapon;
import org.example.controller.ChangeMenuController;
import org.example.controller.GameMenuController;

import java.util.Map;

public class EndGameView implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton backButton;
    private Game game;
    private int type;

    public EndGameView(Game game, int type) {
        this.game = game;
        this.type = type;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\hint.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();


        String mainTitle = "";
        switch(type) {
            case 1: mainTitle = "WIN"; break;
            case 2: mainTitle = "DEAD"; break;
            case 3: mainTitle = "GAVE UP"; break;
        }

        Label titleLabel = new Label(mainTitle, skin);
        titleLabel.setFontScale(3f);
        titleLabel.setColor(Color.GOLD);


        String statsText = String.format(
                "Username: %s\nSurvival Time: %.2f\nKills: %d\nScore: %.2f",
                App.currentUser.username,
                game.time,
                game.kills,
                game.kills * game.time
        );

        Label statsLabel = new Label(statsText, skin);
        statsLabel.setFontScale(1.5f);
        statsLabel.setColor(Color.CYAN);

        mainTable.add(titleLabel).padBottom(50).row();
        mainTable.add(statsLabel).padBottom(30).row();


        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(EndGameView.this, new MainMenu());
            }
        });


        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().right().pad(20);
        buttonTable.add(backButton).size(300, 60);

        stage.addActor(mainTable);
        stage.addActor(buttonTable);
        Gdx.input.setInputProcessor(stage);
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
