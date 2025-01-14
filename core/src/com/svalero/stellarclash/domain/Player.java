package com.svalero.stellarclash.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.svalero.stellarclash.manager.ConfigurationManager;
import com.svalero.stellarclash.manager.ResourceManager;

import static com.svalero.stellarclash.util.Constants.*;

public class Player extends Character{
    public int score;
    public int lives;
    public Array<Bullet> bullets;
    Sound bulletSound;
    private boolean tripleShotActive;


    public Player(String animationName){
        super(new Vector2(0,0), animationName);
        initializeLives();
        score = 0;
        bullets = new Array<>();
        tripleShotActive = false;
    }

    //Constructor que hereda de la clase padre.
    public Player (Vector2 position, String animationName){
        super(position, animationName);
        initializeLives();
        score = 0;
        bullets = new Array<>();
    }

    private void initializeLives() {
        String difficulty = ConfigurationManager.getDifficulty();

        switch (difficulty) {
            case LOW:
                lives = 5;
                break;
            case MEDIUM:
                lives = 3;
                break;
            case HARD:
                lives = 1;
                break;
            default:
                lives = 3;
                break;
        }
    }
    public void setTripleShot(boolean active) {
        this.tripleShotActive = active;
    }

    //Esto, al gestionar los movimientos, se lo queda la clase jugador
    public void manageInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            move(20,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            move(-20,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            move(0,20);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            move(0,-20);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shoot();
            bulletSound = ResourceManager.getSound("pistola");
            if (ConfigurationManager.isSoundEnabled())
                bulletSound.play();
        }

        // Controlamos que el jugador se salga de la pantalla
        float playerWidth = rect.getWidth();
        float playerHeight = rect.getHeight();

        // Limitar la posición en X
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > Gdx.graphics.getWidth() - playerWidth) {
            position.x = Gdx.graphics.getWidth() - playerWidth;
        }
        // Limitar la posición en Y
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y > Gdx.graphics.getHeight() - playerHeight) {
            position.y = Gdx.graphics.getHeight() - playerHeight;
        }

        //Actualizamos balas
        updateBullets(Gdx.graphics.getDeltaTime());
        //No olvidarse rect, que si no fallan las colisiones
        rect.setPosition(position.x, position.y);
    }

    private void shoot() {
        Vector2 bulletPosition = new Vector2(position.x + rect.width, position.y + rect.height / 2);
        bullets.add(new Bullet(bulletPosition));

        if (tripleShotActive) {
            // Disparo hacia arriba
            bullets.add(new Bullet(new Vector2(bulletPosition.x, bulletPosition.y + 20)));
            // Disparo hacia abajo
            bullets.add(new Bullet(new Vector2(bulletPosition.x, bulletPosition.y - 20)));
        }
    }

    private void updateBullets(float deltaTime) {
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (bullet.isOffScreen()) {
                bullets.removeIndex(i);
            }
        }
    }

    public void drawBullets(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

}
