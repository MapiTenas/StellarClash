package com.svalero.stellarclash.domain;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.svalero.stellarclash.manager.ConfigurationManager;
import com.svalero.stellarclash.manager.ResourceManager;

public class PowerUpPuntosExtra extends PowerUp {

    private long spawnTime;
    Sound extraPoints;


    public PowerUpPuntosExtra(Vector2 position) {
        super(position, "puntosextras");
        spawnTime = TimeUtils.nanoTime();
    }

    @Override
    public void applyEffect(Player player) {
        extraPoints = ResourceManager.getSound("extrapointssound");
        if (ConfigurationManager.isSoundEnabled())
            extraPoints.play();
        player.score += 3;
    }

    public boolean shouldDisappear() {
        return TimeUtils.nanoTime() - spawnTime > 6_000_000_000L;
    }
}
