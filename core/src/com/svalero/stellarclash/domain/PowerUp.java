package com.svalero.stellarclash.domain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.svalero.stellarclash.manager.ResourceManager;

public abstract class PowerUp {
    public Vector2 position;  // Posición del power-up
    public Rectangle rect;  // Rectángulo de colisión
    public TextureRegion texture;  // Textura del power-up

    public PowerUp(Vector2 position, String textureName) {
        this.position = position;
        this.texture = ResourceManager.getTexture(textureName);
        this.rect = new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    // Método abstracto para definir el efecto del power-up
    public abstract void applyEffect(Player player);

    // Método para dibujar el power-up
    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    // Método para actualizar el movimiento del power-up (si es necesario)
    public void update(float deltaTime) {
        // Mueve el power-up hacia la izquierda
        position.x -= 100 * deltaTime;
        rect.setPosition(position.x, position.y);
    }
}
