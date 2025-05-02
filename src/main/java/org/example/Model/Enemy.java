package org.example.Model;

public class Enemy {
    public float x;
    public float y;
    public String texturePath;
    public int hp;

    public Enemy(float x, float y, String texturePath, int hp) {
        this.x = x;
        this.y = y;
        this.texturePath = texturePath;
        this.hp = hp;
    }
}
