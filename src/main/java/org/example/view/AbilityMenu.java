package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import org.example.Model.Game;
import org.example.Model.enums.Ability;
import org.example.controller.ChangeMenuController;
import org.example.controller.PauseMenuController;

import java.util.ArrayList;

public class AbilityMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton resumeButton, blackWhiteButton, giveUpButton, saveExitButton;
    private Game game;

    public AbilityMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pauseMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.center();
        int randNum = (int)(Math.random()* 4);
        ArrayList<Ability> abilities = new ArrayList<>();
        if(randNum == 0) {
            abilities.add(Ability.AMOCREASE);
            abilities.add(Ability.DAMAGER);
            abilities.add(Ability.PROCREASE);
        }
        else if(randNum == 1) {
            abilities.add(Ability.DAMAGER);
            abilities.add(Ability.VITALITY);
            abilities.add(Ability.SPEEDY);
        }
        else if(randNum == 2) {
            abilities.add(Ability.PROCREASE);
            abilities.add(Ability.VITALITY);
            abilities.add(Ability.SPEEDY);
        }
        else  {
            abilities.add(Ability.VITALITY);
            abilities.add(Ability.AMOCREASE);
            abilities.add(Ability.SPEEDY);
        }


        resumeButton = new TextButton(abilities.getFirst().name, skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.abilitiesQueue.add(abilities.getFirst());
                game.abilities.add(abilities.getFirst());
                ChangeMenuController.changeMenu(AbilityMenu.this, App.currentGame.gameMenu);
            }
        });

        blackWhiteButton = new TextButton(abilities.get(1).name, skin);
        blackWhiteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.abilitiesQueue.add(abilities.get(1));
                game.abilities.add(abilities.get(1));
                ChangeMenuController.changeMenu(AbilityMenu.this, App.currentGame.gameMenu);
            }
        });

        giveUpButton = new TextButton(abilities.get(2).name, skin);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.abilitiesQueue.add(abilities.get(2));
                game.abilities.add(abilities.get(2));
                ChangeMenuController.changeMenu(AbilityMenu.this, App.currentGame.gameMenu);
            }
        });

        buttonTable.row();
        buttonTable.add(resumeButton).size(400, 100).padBottom(10);
        buttonTable.row();
        buttonTable.add(blackWhiteButton).size(400, 100).padBottom(10);
        buttonTable.row();
        buttonTable.add(giveUpButton).size(400, 100).padBottom(10);

        stage.addActor(buttonTable);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
//        if (App.currentGame.isBlackWhite) {
//            stage.getBatch().setShader(App.currentGame.blackAndWhiteShader);
//        } else {
        stage.getBatch().setShader(null);
        //     }
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
        backgroundTexture.dispose();
    }
}
