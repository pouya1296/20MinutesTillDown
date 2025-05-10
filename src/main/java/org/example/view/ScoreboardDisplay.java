package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.App;
import org.example.Model.User;
import org.example.Model.enums.Language;
import org.example.controller.ChangeMenuController;

import java.util.List;

public class ScoreboardDisplay implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton backButton;
    private List<User> users;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Color GOLD = new Color(1f, 0.84f, 0f, 1f);
    private Color SILVER = new Color(0.75f, 0.75f, 0.75f, 1f);
    private Color BRONZE = new Color(0.8f, 0.5f, 0.2f, 1f);

    public ScoreboardDisplay(List<User> users) {
        this.users = users;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        table = new Table();
        table.setFillParent(true);
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\messi.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table headerTable = new Table(skin);
        headerTable.add(Language.RANK.languages[App.languageNumber]).pad(5).left();
        headerTable.add(Language.AVATAR.languages[App.languageNumber]).pad(5).left();
        headerTable.add(Language.USERNAME.languages[App.languageNumber]).pad(5).left();
        headerTable.add(Language.SCORE.languages[App.languageNumber]).pad(5).left();
        headerTable.add(Language.KILLS.languages[App.languageNumber]).pad(5).left();
        headerTable.add(Language.SURVIVAL_TIME.languages[App.languageNumber]).pad(5).left();
        table.add(headerTable).padBottom(10);
        table.row();

        int displayLimit = Math.min(10, users.size());

        for (int i = 0; i < displayLimit; i++) {
            User user = users.get(i);
            Table row = new Table(skin);
            Label rankLabel = new Label(String.valueOf(i + 1), skin);
            Image avatarImage = new Image(new Texture(Gdx.files.internal(user.avatarPath)));
            Label usernameLabel = new Label(user.getUsername(), skin);
            Label scoreLabel = new Label(String.valueOf(user.getScore()), skin);
            Label killsLabel = new Label(String.valueOf(user.getTotalKills()), skin);
            Label survivalLabel = new Label(String.valueOf(user.getSurvivalTime()), skin);
            if (i == 0) {
                usernameLabel.setColor(GOLD);
            } else if (i == 1) {
                usernameLabel.setColor(SILVER);
            } else if (i == 2) {
                usernameLabel.setColor(BRONZE);
            } else {
                usernameLabel.setColor(Color.WHITE);
            }

            if (user.getUsername().equals(App.currentUser.getUsername())) {
                usernameLabel.setColor(Color.GREEN);
            }
            row.add(rankLabel).pad(5).align(Align.left).expandX().uniform();
            row.add(avatarImage).size(50, 50).pad(5).align(Align.left).expandX().uniform();
            row.add(usernameLabel).pad(5).align(Align.left).expandX().uniform();
            row.add(scoreLabel).pad(5).align(Align.left).expandX().uniform();
            row.add(killsLabel).pad(5).align(Align.left).expandX().uniform();
            row.add(survivalLabel).pad(5).align(Align.left).expandX().uniform();

            table.add(row).expandX().fillX().left();
            table.row();
        }


        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(ScoreboardDisplay.this, new MainMenu());
            }
        });

        Table footer = new Table();
        footer.add(backButton).size(400, 100).padTop(20);
        table.row();
        table.add(footer).expand().bottom();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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
}
