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
import org.example.Model.enums.Ability;
import org.example.Model.enums.Hero;
import org.example.Model.enums.Language;
import org.example.Model.enums.Weapon;
import org.example.controller.ChangeMenuController;

import java.util.Map;

public class HintMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton backButton;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\pixthulhu\\skin\\pixthulhu-ui.json"));
        backgroundTexture = new Texture(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\hint.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Label titleLabel = new Label(Language.GAME_INFO.languages[App.languageNumber], skin);
        titleLabel.setColor(Color.YELLOW);
        titleLabel.setFontScale(1.5f);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(titleLabel).padBottom(20);
        table.row();

        addHeroInfo(table);
        addWeaponInfo(table);
        addGameKeys(table);
        addAbilities(table);
//        addCheatCodes(table);

        backButton = new TextButton(Language.BACK_TO_MAIN_MENU.languages[App.languageNumber], skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChangeMenuController.changeMenu(HintMenu.this, new MainMenu());
            }
        });

        table.row();
        table.add(backButton).size(300, 50).padTop(30);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void addHeroInfo(Table table) {
        Label heroTitle = new Label("Heroes:", skin);
        heroTitle.setColor(Color.CYAN);
        table.row();
        table.add(heroTitle).padBottom(10);

        for (Hero hero : Hero.values()) {
            Label heroLabel = new Label(hero.name + " (MaxHp: " + hero.maxHP + ", Speed: " + hero.speed + ")", skin);
            table.row();
            table.add(heroLabel).padBottom(5);
        }
    }

    private void addWeaponInfo(Table table) {
        Label weaponTitle = new Label("Weapons:", skin);
        weaponTitle.setColor(Color.ORANGE);
        table.row();
        table.add(weaponTitle).padBottom(10);

        for (Weapon weapon : Weapon.values()) {
            Label weaponLabel = new Label(weapon.name + " (Damage: " + weapon.damage + ", Projectile: " + weapon.projectile + ", ReloadTime: " + weapon.reloadTime + ", Ammo: " + weapon.maxAmmo + ")", skin);
            table.row();
            table.add(weaponLabel).padBottom(5);
        }
    }

    private void addGameKeys(Table table) {
        Label keyTitle = new Label(Language.GAME_CONTROLS.languages[App.languageNumber], skin);
        keyTitle.setColor(Color.GREEN);
        table.row();
        table.add(keyTitle).padBottom(10);

        for (Map.Entry<String, Integer> entry : App.currentUser.keys.entrySet()) {
            Label keyLabel = new Label(entry.getKey() + " -> " + Input.Keys.toString(entry.getValue()), skin);
            table.row();
            table.add(keyLabel).padBottom(5);
        }
    }

    private void addAbilities(Table table) {
        Label abilityTitle = new Label("Abilities:", skin);
        abilityTitle.setColor(Color.MAGENTA);
        table.row();
        table.add(abilityTitle).padBottom(10);

        for (Ability ability : Ability.values()) {
            Label abilityLabel = new Label(ability.name + ": " + getAbilityDescription(ability), skin);
            table.row();
            table.add(abilityLabel).padBottom(5);
        }
    }

    private String getAbilityDescription(Ability ability) {
        if (ability == Ability.VITALITY)
            return "Increase maximum HP by 1.";
        if (ability == Ability.DAMAGER)
            return "Increase weapon damage by 25% for 10 seconds.";
        if (ability == Ability.PROCREASE)
            return "Increase projectile by 1.";
        if (ability == Ability.AMOCREASE)
            return "Increase maximum Ammo by 5.";
        return "Double the player's speed for 10 seconds.";

    }

//    private void addCheatCodes(Table table) {
//        Label cheatTitle = new Label("Cheat Codes:", skin);
//        cheatTitle.setColor(Color.RED);
//        table.row();
//        table.add(cheatTitle).padBottom(10);
//
//        for (String cheatKey : new String[]{"cheet1", "cheet2", "cheet3"}) {
//            if (App.currentUser.keys.containsKey(cheatKey)) {
//                Label cheatLabel = new Label(cheatKey + " -> " + Input.Keys.toString(App.currentUser.keys.get(cheatKey)), skin);
//                table.row();
//                table.add(cheatLabel).padBottom(5);
//            }
//        }
//    }

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
