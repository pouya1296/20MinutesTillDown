package org.example.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Drop {
    public float x, y;
    public String texturePath;
    public boolean collected = false;
    public Rectangle bounds;
    public transient Texture texture;
    public int textureID;

    public static final int XP_VALUE = 3;

    public Drop(float x, float y, String texturePath, int textureID) {
        this.x = x;
        this.y = y;
        this.texturePath = texturePath;
        this.textureID = textureID;
        this.texture = new Texture(texturePath);
        this.bounds = new Rectangle(x,  y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch, Texture texture) {
        if (!collected) {
            batch.draw(texture, x, y);
        }
    }

    public void updateBounds() {
        this.bounds.setPosition( x,  y);
    }
}
