package com.svalero.stellarclash.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.svalero.stellarclash.manager.ResourceManager;

public class Bullet {
    public Vector2 position;
    public Rectangle rect;
    private TextureRegion texture;
    private float speed;

    public Bullet(Vector2 position) {
        this.position = position;
        this.speed = 500;
        this.texture = ResourceManager.getTexture("bullet");
        this.rect = new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    public void update(float deltaTime) {
        position.x += speed * deltaTime;
        rect.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean isOffScreen() {
        return position.x > Gdx.graphics.getWidth();
    }
}
