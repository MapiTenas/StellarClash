package com.svalero.stellarclash.domain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.svalero.stellarclash.manager.ConfigurationManager;
import com.svalero.stellarclash.manager.ResourceManager;

public class PowerUpDisparoTriple extends PowerUp {
    Sound tripleShoot;


    public PowerUpDisparoTriple(Vector2 position) {
        super(position, "disparotriple");
    }

    @Override
    public void applyEffect(final Player player) {
        player.setTripleShot(true);
        tripleShoot= ResourceManager.getSound("tripleshotsound");
        if (ConfigurationManager.isSoundEnabled())
            tripleShoot.play();
        // 10 segundos
        Timer.schedule(new Task(){
            @Override
            public void run() {
                player.setTripleShot(false);
            }
        }, 10);
    }
}
