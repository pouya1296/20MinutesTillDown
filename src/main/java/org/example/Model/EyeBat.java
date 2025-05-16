package org.example.Model;

public class EyeBat extends Enemy {
    public EyeBat(){
    }
    public float shootTimer;
    public EyeBat(float x, float y, String[] texturePath, int hp) {
        super(x, y, texturePath, hp, org.example.Model.enums.Enemy.EYE_BAT.texturePaths[4]);
        this.saveType = "EyeBat";
        shootTimer = 0f;
    }

    @Override
    public void update(float delta, Game game) {
        if(stage == 0)
        stateTime += delta;
        else
            stage += delta;
    }
}
