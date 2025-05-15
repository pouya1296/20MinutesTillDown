package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Elder extends Enemy {
    public Elder(float x, float y, String[] texturePath, int hp) {
        super(x, y, texturePath, hp, org.example.Model.enums.Enemy.ELDER.texturePaths[1]);
    }

    @Override
    public void render(SpriteBatch batch, Animation<TextureRegion> walkAnimation, Animation<TextureRegion> elimintationAnimation) {
        if(stage == 0) {
            Texture elderTexture = new Texture(Gdx.files.internal(texturePath[0]));
            batch.draw(elderTexture, x, y, 100, 100);
        }
        else  {
            TextureRegion currentFrame = elimintationAnimation.getKeyFrame(stage , true);
            batch.draw(currentFrame, x, y);;
        }
    }

    @Override
    public void update(float delta, Game game) {
        if(stage == 0) {
            if (game.elderDashTimer >= 5f) {
                game.elderDashTimer = 0f;
                float dx = game.x - this.x;
                float dy = game.y - this.y;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                float distanceX = (dx / dist) * game.enemySpeed * delta * 700;
                float distanceY = (dy / dist) * game.enemySpeed * delta * 700;
                if (Math.abs(distanceX) > Math.abs(dx)) {
                    distanceX = dx;
                }
                if (Math.abs(distanceY) > Math.abs(dy)) {
                    distanceY = dy;
                }
                if (dist > 0) {
                    this.x += distanceX;
                    this.y += distanceY;
                }
            }
        }
        else
            stage+= delta;
    }
}
