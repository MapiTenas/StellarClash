package com.svalero.stellarclash.domain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.svalero.stellarclash.manager.ResourceManager;

public class EnemyBullet {
    public Vector2 position;
    public Rectangle rect;
    private TextureRegion texture;
    private float speed;

    public EnemyBullet(Vector2 position) {
        this.position = position;
        this.speed = 500; // Velocidad de la bala
        this.texture = ResourceManager.getTexture("enemybullet"); // Textura de la bala enemiga
        this.rect = new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    public void update(float deltaTime) {
        position.x -= speed * deltaTime; // Mover la bala hacia la izquierda
        rect.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean isOffScreen() {
        return position.x < 0; // Verificar si la bala se sale por la izquierda de la pantalla
    }
}