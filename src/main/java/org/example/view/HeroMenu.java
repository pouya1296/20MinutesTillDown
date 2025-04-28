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
import org.example.Model.enums.Hero;

public class HeroMenu implements Screen, InputProcessor {
    private Stage stage;
    private Skin skin;
    private Label selectedHeroLabel;
    private TextButton shanaButton, diamondButton, scarletButton, lilithButton, dasherButton, backButton;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private int index = 0;
    private Hero[] heroes = {Hero.SHANA, Hero.DIAMOND, Hero.SCARLET, Hero.LILITH, Hero.DASHER};

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\PreGame.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        selectedHeroLabel = new Label("Selected Hero: " + App.currentUser.hero, skin);
        selectedHeroLabel.setColor(Color.YELLOW);

        shanaButton = new TextButton("SHANA", skin);
        diamondButton = new TextButton("DIAMOND", skin);
        scarletButton = new TextButton("SCARLET", skin);
        lilithButton = new TextButton("LILITH", skin);
        dasherButton = new TextButton("DASHER", skin);
        backButton = new TextButton("Back to PreGame Menu", skin);

        addClickListeners();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(selectedHeroLabel).pad(10);
        table.row();
        table.add(shanaButton).size(250, 50).pad(10);
        table.row();
        table.add(diamondButton).size(250, 50).pad(10);
        table.row();
        table.add(scarletButton).size(250, 50).pad(10);
        table.row();
        table.add(lilithButton).size(250, 50).pad(10);
        table.row();
        table.add(dasherButton).size(250, 50).pad(10);
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
        ClickListener shanaClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentUser.hero = heroes[index];
                selectedHeroLabel.setText("Selected Hero: " + App.currentUser.hero.name);
                updateSelection();
            }
        };
        ClickListener diamondClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 1;
                App.currentUser.hero = heroes[index];
                selectedHeroLabel.setText("Selected Hero: " + App.currentUser.hero.name);
                updateSelection();
            }
        };
        ClickListener scarletClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 2;
                App.currentUser.hero = heroes[index];
                selectedHeroLabel.setText("Selected Hero: " + App.currentUser.hero.name);
                updateSelection();
            }
        };
        ClickListener lilithClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 3;
                App.currentUser.hero = heroes[index];
                selectedHeroLabel.setText("Selected Hero: " + App.currentUser.hero.name);
                updateSelection();
            }
        };
        ClickListener dasherClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = 4;
                App.currentUser.hero = heroes[index];
                selectedHeroLabel.setText("Selected Hero: " + App.currentUser.hero.name);
                updateSelection();
            }
        };


        shanaButton.addListener(shanaClickListener);
        diamondButton.addListener(diamondClickListener);
        scarletButton.addListener(scarletClickListener);
        lilithButton.addListener(lilithClickListener);
        dasherButton.addListener(dasherClickListener);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreGameMenu());
            }
        });
    }

    private void updateSelection() {
        shanaButton.setColor(index == 0 ? Color.RED : Color.WHITE);
        diamondButton.setColor(index == 1 ? Color.RED : Color.WHITE);
        scarletButton.setColor(index == 2 ? Color.RED : Color.WHITE);
        lilithButton.setColor(index == 3 ? Color.RED : Color.WHITE);
        dasherButton.setColor(index == 4 ? Color.RED : Color.WHITE);
        backButton.setColor(index == 5 ? Color.RED : Color.WHITE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            index = (index + 1) % (heroes.length + 1);
        } else if (keycode == Input.Keys.UP) {
            index = (index - 1 + (heroes.length + 1)) % (heroes.length + 1);
        } else if (keycode == Input.Keys.ENTER) {
            if (index == 5) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreGameMenu());
            } else {
                App.currentUser.hero = heroes[index];
                selectedHeroLabel.setText("Selected Hero: " + heroes[index].name);
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
