package com.svalero.stellarclash.domain;

import com.badlogic.gdx.math.Vector2;

public class PowerUpVida extends PowerUp {

    public PowerUpVida(Vector2 position) {
        super(position, "vida");  // Usamos la textura "vida" para este power-up
    }

    @Override
    public void applyEffect(Player player) {
        player.lives++;  // Aumenta la vida del jugador en 1
    }
}
