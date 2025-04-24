package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class User {
    public String username;
    public String password;
    public String question;
    public String answer;
    public Texture texture;
    public String avatarPath;

    public User(String username, String password, String question, String answer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatarPath = avatarPath;
        texture = new Texture(Gdx.files.internal(avatarPath));
    }
}
