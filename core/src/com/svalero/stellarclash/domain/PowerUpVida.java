package com.svalero.stellarclash.domain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.svalero.stellarclash.manager.ConfigurationManager;
import com.svalero.stellarclash.manager.ResourceManager;

public class PowerUpVida extends PowerUp {
    Sound extraLife;

    public PowerUpVida(Vector2 position) {
        super(position, "vida");  // Usamos la textura "vida" para este power-up
    }

    @Override
    public void applyEffect(Player player) {
        extraLife = ResourceManager.getSound("extralifesound");
        if (ConfigurationManager.isSoundEnabled())
            extraLife.play();
        player.lives++;  // Aumenta la vida del jugador en 1
    }
}
