package org.example.view;

import com.badlogic.gdx.Gdx;
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
import org.example.Model.User;
import org.example.Model.enums.Ability;
import org.example.controller.ChangeMenuController;
import org.example.controller.DatabaseManager;
import org.example.controller.GameDataManager;
import org.example.controller.PauseMenuController;

import java.sql.SQLException;

public class PauseMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton resumeButton, blackWhiteButton, giveUpButton, saveExitButton;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pauseMenu.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        table.left().top();

        addAbilities(table);
        //addCheatCodes(table);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.center();

        resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(PauseMenu.this, App.currentGame.gameMenu);
            }
        });

        blackWhiteButton = new TextButton("Black and White", skin);
        blackWhiteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentGame.isBlackWhite = !App.currentGame.isBlackWhite;
            }
        });

        giveUpButton = new TextButton("Give Up", skin);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PauseMenuController.giveUpHandle(PauseMenu.this);
            }
        });

        saveExitButton = new TextButton("Save and Exit", skin);
        saveExitButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                PauseMenuController.saveAndExitHandle(PauseMenu.this);
            }
        });

        buttonTable.row();
        buttonTable.add(resumeButton).size(400, 100).padBottom(10);
        buttonTable.row();
        buttonTable.add(blackWhiteButton).size(400, 100).padBottom(10);
        buttonTable.row();
        buttonTable.add(giveUpButton).size(400, 100).padBottom(10);
        buttonTable.row();
        buttonTable.add(saveExitButton).size(400, 100).padBottom(10);

        stage.addActor(table);
        stage.addActor(buttonTable);
        Gdx.input.setInputProcessor(stage);
    }


    private void addAbilities(Table table) {
        Label abilityTitle = new Label("Abilities:", skin);
        abilityTitle.setColor(Color.MAGENTA);
        table.row();
        table.add(abilityTitle).padBottom(10);

        for (Ability ability : App.currentGame.abilities) {
            Label abilityLabel = new Label(ability.name + ": " + getAbilityDescription(ability), skin);
            table.row();
            table.add(abilityLabel).padBottom(5);
        }
    }

//    private void addCheatCodes(Table table) {
//        Label cheatTitle = new Label("Cheat Codes:", skin);
//        cheatTitle.setColor(Color.RED);
//        table.row();
//        table.add(cheatTitle).padBottom(10);
//
//        for (String cheatKey : new String[]{"cheat1", "cheat2", "cheat3"}) {
//            if (App.currentUser.keys.containsKey(cheatKey)) {
//                Label cheatLabel = new Label(cheatKey + " -> " + Input.Keys.toString(App.currentUser.keys.get(cheatKey)), skin);
//                table.row();
//                table.add(cheatLabel).padBottom(5);
//            }
//        }
//    }

    private String getAbilityDescription(Ability ability) {
        if (ability == Ability.VITALITY) return "Increase maximum HP by 1.";
        if (ability == Ability.DAMAGER) return "Increase weapon damage by 25% for 10 seconds.";
        if (ability == Ability.PROCREASE) return "Increase projectile by 1.";
        if (ability == Ability.AMOCREASE) return "Increase maximum Ammo by 5.";
        return "Double the player's speed for 10 seconds.";
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
        stage.clear();
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
