package com.svalero.stellarclash.domain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.svalero.stellarclash.manager.ConfigurationManager;

import static com.svalero.stellarclash.util.Constants.*;

public class EnemyFinalBoss extends Enemy{
    public int lives;
    public Array<EnemyBullet> bullets;
    private long lastShotTime;
    private float timeBetweenShots;

    public EnemyFinalBoss(Vector2 position, String animationName) {
        super(position, animationName);
        initializeLives();
        bullets = new Array<>();
        timeBetweenShots = 1.5f * 1000000000; // Tiempo entre disparos en nanosegundos (1.5 segundos)
        lastShotTime = TimeUtils.nanoTime();
    }

    private void initializeLives(){
        String difficulty = ConfigurationManager.getDifficulty();
        switch (difficulty) {
            case LOW:
                lives = 5;
                break;
            case MEDIUM:
                lives = 7;
                break;
            case HARD:
                lives = 9;
                break;
            default:
                lives = 7;
                break;
        }
    }

    // Método para disparar
    public void shoot() {
        Vector2 bulletPosition = new Vector2(position.x, position.y + rect.height / 2);
        bullets.add(new EnemyBullet(bulletPosition));
    }

    // Método para actualizar las balas
    public void updateBullets(float deltaTime) {
        for (int i = bullets.size - 1; i >= 0; i--) {
            EnemyBullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (bullet.isOffScreen()) {
                bullets.removeIndex(i);
            }
        }
    }

    // Método para gestionar disparos automáticos
    public void tryToShoot() {
        if (TimeUtils.nanoTime() - lastShotTime > timeBetweenShots) {
            shoot();
            lastShotTime = TimeUtils.nanoTime();
        }
    }
}
