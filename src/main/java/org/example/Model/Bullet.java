package org.example.Model;

import com.badlogic.gdx.Gdx;
import org.example.Model.enums.Weapon;

public class Bullet {
    public float x, y;
    public float dx, dy;
    public float speed;
    public boolean active = true;

    public Bullet(float startX, float startY, float targetX, float targetY) {
        this.x = startX;
        this.y = startY;
        speed = 400;
        float angle = (float) Math.atan2(targetY - startY, targetX - startX);
        dx = (float) Math.cos(angle);
        dy = (float) Math.sin(angle);
    }

    public void update(float delta) {
        x += dx * speed * delta;
        y += dy * speed * delta;


        if (x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight()) {
            active = false;
        }
    }
}