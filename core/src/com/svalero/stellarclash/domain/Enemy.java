package com.svalero.stellarclash.domain;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Character {
    public Enemy(Vector2 position, String animationName) {
        super(position, animationName);
    }
}
