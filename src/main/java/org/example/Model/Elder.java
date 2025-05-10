package org.example.Model;

public class Elder extends Enemy{
    public Elder(float x, float y, String[] texturePath, int hp) {
        super(x, y, texturePath, hp, org.example.Model.enums.Enemy.ELDER.texturePaths[1]);
    }
}
