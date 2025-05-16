package org.example.Model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    protected transient String saveType;
    public float x;
    public float y;
    public String[] texturePath;
    public int hp;
    public float stateTime = 0f;
    public String dropPath;
    public float stage;

    public Enemy(){

    }

    public Enemy(float x, float y, String[] texturePath, int hp, String dropPath) {
        this.x = x;
        this.y = y;
        this.texturePath = texturePath;
        this.hp = hp;
        this.dropPath = dropPath;
        this.stage = 0;
    }

    public void render(SpriteBatch batch,Animation<TextureRegion> walkAnimation, Animation<TextureRegion> eliminationAnimation) {
        if(stage == 0) {
            TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
        }
        else {
            TextureRegion currentFrame = eliminationAnimation.getKeyFrame(stage , true);
            batch.draw(currentFrame, x, y);
        }

    }

    public void update(float delta, Game game) {
        if(stage == 0) {
            stateTime += delta;
            float dx = game.x - this.x;
            float dy = game.y - this.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > 0) {
                this.x += (dx / dist) * game.enemySpeed * delta;
                this.y += (dy / dist) * game.enemySpeed * delta;
            }
        }
        else
            stage += delta;
    }
}
