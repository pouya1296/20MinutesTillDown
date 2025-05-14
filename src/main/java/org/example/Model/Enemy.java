package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import jakarta.persistence.Transient;

import java.util.Iterator;

public class Enemy {
    public float x;
    public float y;
    public String[] texturePath;
    public int hp;
    public float stateTime = 0f;
    public String dropPath;

    public Enemy(float x, float y, String[] texturePath, int hp, String dropPath) {
        this.x = x;
        this.y = y;
        this.texturePath = texturePath;
        this.hp = hp;
        this.dropPath = dropPath;
    }

    public void render(SpriteBatch batch,Animation<TextureRegion> walkAnimation) {
            TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
    }

    public void update(float delta, Game game) {
        stateTime += delta;
            float dx = game.x - this.x;
            float dy = game.y - this.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > 0) {
                this.x += (dx / dist) * game.enemySpeed * delta;
                this.y += (dy / dist) * game.enemySpeed * delta;
            }
        }
}
