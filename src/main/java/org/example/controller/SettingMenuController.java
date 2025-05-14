package org.example.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import org.example.Model.App;
import org.example.view.KeyboardMenu;
import org.example.view.MainMenu;
import org.example.view.MusicMenu;
import org.example.view.SettingsMenu;

public class SettingMenuController {

    public static void checkEnter(int index, CheckBox soundEffectCheckBox ,CheckBox autoReloadCheckBox,CheckBox grayscaleCheckBox, SettingsMenu settingsMenu) {
        if (index == 0) {
            if (MusicManager.getBackgroundMusic().isPlaying())
                MusicManager.stop();
            else
                MusicManager.play();
        } else if (index == 1) {
            ChangeMenuController.changeMenu(settingsMenu, new MusicMenu());
        } else if (index == 2) {
            App.currentUser.soundEffect = !App.currentUser.soundEffect;
            soundEffectCheckBox.setChecked(App.currentUser.soundEffect);
        } else if (index == 3) {
            ChangeMenuController.changeMenu(settingsMenu, new KeyboardMenu(App.currentUser.keys));
        } else if (index == 4) {
            App.currentUser.autoReload = !App.currentUser.autoReload;
            autoReloadCheckBox.setChecked(App.currentUser.autoReload);
        } else if (index == 5) {
            App.currentUser.grayScaleMode = !App.currentUser.grayScaleMode;
            grayscaleCheckBox.setChecked(App.currentUser.grayScaleMode);
        } else if (index == 7) {
            ChangeMenuController.changeMenu(settingsMenu, new MainMenu());
        }
    }


    public static boolean volumeControl(int keycode, Slider volumeSlider) {
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
        return false;
    }


}
