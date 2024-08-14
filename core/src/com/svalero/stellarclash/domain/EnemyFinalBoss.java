package com.svalero.stellarclash.domain;

import com.badlogic.gdx.math.Vector2;

public class EnemyFinalBoss extends Enemy{
    public int lives;

    public EnemyFinalBoss(Vector2 position, String animationName) {
        super(position, animationName);
        lives = 5;
    }
}
