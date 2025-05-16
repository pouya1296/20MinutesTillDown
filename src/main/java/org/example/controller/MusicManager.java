package org.example.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static Music backgroundMusic;

    public static void initialize() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\music\\star.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
    }

    public static void play() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public static Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void stop() {
        backgroundMusic.stop();
    }

    public static void setVolume(float volume) {
        backgroundMusic.setVolume(volume);
    }

    public static void changeMusic(String newMusicPath) {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(newMusicPath));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }
}
