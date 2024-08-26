package com.svalero.stellarclash.domain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PowerUpDisparoTriple extends PowerUp {

    public PowerUpDisparoTriple(Vector2 position) {
        super(position, "disparotriple");
    }

    @Override
    public void applyEffect(final Player player) {
        player.setTripleShot(true);

        // 10 segundos
        Timer.schedule(new Task(){
            @Override
            public void run() {
                player.setTripleShot(false);
            }
        }, 10);
    }
}
