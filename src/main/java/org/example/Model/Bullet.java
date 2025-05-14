package org.example.Model;

import com.badlogic.gdx.Gdx;

public class Bullet {
    public float x, y;
    public float dx, dy;
    public float speed;
    public boolean active = true;

    public Bullet(float startX, float startY, float targetX, float targetY) {
        this.x = startX;
        this.y = startY;
        speed = 450;
        float angle = (float) Math.atan2(targetY - startY, targetX - startX);
        dx = (float) Math.cos(angle);
        dy = (float) Math.sin(angle);
    }

    public void update(float delta, Game game) {
        x += dx * speed * delta;
        y += dy * speed * delta;


        if (x - game.x < -(float) Gdx.graphics.getWidth() * 2 || -game.x + x > (float) Gdx.graphics.getWidth() * 2 || -game.y + y < -(float) Gdx.graphics.getHeight() * 2 || -game.y + y > (float) Gdx.graphics.getHeight() * 2) {
            active = false;
        }
    }
}