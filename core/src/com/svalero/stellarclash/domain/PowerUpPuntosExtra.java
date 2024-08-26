package com.svalero.stellarclash.domain;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class PowerUpPuntosExtra extends PowerUp {

    private long spawnTime;  // Tiempo en el que apareció el PowerUp

    public PowerUpPuntosExtra(Vector2 position) {
        super(position, "puntosextras");  // Usamos la textura "puntosextras" para este power-up
        spawnTime = TimeUtils.nanoTime();  // Guardamos el momento en el que aparece el power-up
    }

    @Override
    public void applyEffect(Player player) {
        player.score += 3;  // Suma 10 puntos al score del jugador
    }

    // Método para verificar si el power-up debe desaparecer
    public boolean shouldDisappear() {
        // Si han pasado más de 4 segundos desde que apareció, debe desaparecer
        return TimeUtils.nanoTime() - spawnTime > 5_000_000_000L;
    }
}
