package org.example.Model;

public class EyeBat extends Enemy {
    public float shootTimer;
    public EyeBat(float x, float y, String[] texturePath, int hp) {
        super(x, y, texturePath, hp, org.example.Model.enums.Enemy.EYE_BAT.texturePaths[4]);
        shootTimer = 0f;
    }
}
