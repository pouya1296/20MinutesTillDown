package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TentacleMonster extends Enemy{

    public TentacleMonster() {}

    public TentacleMonster(float x, float y, String[] texturePath, int hp) {
        super(x, y, texturePath, hp, org.example.Model.enums.Enemy.TENTACLE.texturePaths[5]);
        this.saveType = "TentacleMonster";
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
        Texture t = new Texture(Gdx.files.internal(org.example.Model.enums.Enemy.TENTACLE.texturePaths[i]));
            frames[i] = new TextureRegion(t);
        }
    }

}
