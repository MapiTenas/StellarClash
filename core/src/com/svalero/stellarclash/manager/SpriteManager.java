package com.svalero.stellarclash.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.svalero.stellarclash.domain.Bullet;
import com.svalero.stellarclash.domain.Enemy;
import com.svalero.stellarclash.domain.EnemyShip;
import com.svalero.stellarclash.domain.Player;
import com.svalero.stellarclash.screen.MainMenuScreen;

public class SpriteManager implements Disposable {
    //Se encarga de la lógica
    Player player;
    boolean pause;
    Array<Enemy> enemies;
    float lastEnemyShip;
    float timeBetweenEnemiesShip;

    public SpriteManager(){
        initialize();
    }

    private void initialize(){
        player = new Player(new Vector2(0,0),"player");
        pause = false;
        enemies = new Array<>();
        lastEnemyShip = TimeUtils.millis();
        timeBetweenEnemiesShip = 1000000000/2; //(Si divido el numero puedo hacer que salgan a mayor velocidad (¿Para niveles podria estar bien?)
    }

    private void updateEnemies(){
        for (Enemy enemy : enemies) {
            enemy.move(-10, 0);
        }
    }

    private void spawnEnemies(){
        if (TimeUtils.nanoTime() - lastEnemyShip > timeBetweenEnemiesShip)
            spawnShipEnemy();
        //Este metodo llama al otro que spawnea 1 enemigo, pero aqui se podria meter el spawn de otros, si salieran mas.
    }
    private void spawnShipEnemy(){
        int x = Gdx.graphics.getWidth();
        int y = MathUtils.random(0, Gdx.graphics.getHeight());
        Enemy enemy = new EnemyShip(new Vector2(x, y), "enemy"); //Posicion y nombre de la animación
        enemies.add(enemy);

        lastEnemyShip = TimeUtils.nanoTime();

    }

    private void handleCollisions() {
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);

            // Colisiones entre el enemigo y el jugador
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;

                if (player.lives == 0) {
                    pause = true;
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run (){
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                        }
                    }, 2);
                }
                enemies.removeIndex(i);
                continue;
            }
            // Colisiones entre el enemigo y las balas
            for (int j = player.bullets.size - 1; j >= 0; j--) {
                Bullet bullet = player.bullets.get(j);
                if (bullet.rect.overlaps(enemy.rect)) {
                    enemies.removeIndex(i);
                    player.bullets.removeIndex(j);
                    player.score++;
                    break;
                }
            }
        }
    }



    private void handleGameScreenInput(){
        //Ir al menú con ESCAPE
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }

    public void update(float dt){
        if(!pause) {
            spawnEnemies();
            updateEnemies();
            player.manageInput();
            handleCollisions();
        }
        handleGameScreenInput();
    }

    @Override
    public void dispose() {
        player.dispose();
    }
}
