package com.svalero.stellarclash.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.svalero.stellarclash.domain.*;
import com.svalero.stellarclash.screen.MainMenuScreen;

public class SpriteManager implements Disposable {
    //Se encarga de la lógica
    Player player;
    boolean pause;
    Array<Enemy> enemies;
    float lastEnemyShip, lastEnemyAsteroid;
    float timeBetweenEnemiesShip, timeBetweenEnemyAsteroid;
    int level;
    private TextureRegion background;
    private int nextLevelScore;
    private boolean levelChanged;

    public SpriteManager(){
        initialize();
    }

    private void initialize(){
        player = new Player(new Vector2(0,0),"player");
        pause = false;
        background = ResourceManager.getTexture("farback");
        level = 1;
        levelChanged = false;
        nextLevelScore = 15; //Puntuación para pasar al siguiente nivel //Todo: Modificar para que segun el nivel de dificultad escogido cambie
        enemies = new Array<>();
        lastEnemyShip = TimeUtils.millis();
        lastEnemyAsteroid = TimeUtils.millis();
        timeBetweenEnemiesShip = 1000000000/2; //(Si divido el numero puedo hacer que salgan a mayor velocidad (¿Para niveles podria estar bien?)
        timeBetweenEnemyAsteroid = 4 * timeBetweenEnemiesShip;  //Mas lento!
    }

    public TextureRegion getCurrentBackground() {
        return background;
    }

    private void updateEnemies(){
        for (Enemy enemy : enemies) {
            enemy.move(-10, 0);
        }
    }

    private void spawnEnemies(){
        if (TimeUtils.nanoTime() - lastEnemyShip > timeBetweenEnemiesShip)
            spawnShipEnemy();

        if (TimeUtils.nanoTime() - lastEnemyAsteroid > timeBetweenEnemyAsteroid)
            spawnEnemyAsteroid();
    }
    private void spawnShipEnemy(){
        int x = Gdx.graphics.getWidth();
        int y = MathUtils.random(0, Gdx.graphics.getHeight());
        Enemy enemy = new EnemyShip(new Vector2(x, y), "enemy"); //Posicion y nombre de la animación
        enemies.add(enemy);

        lastEnemyShip = TimeUtils.nanoTime();

    }

    private void spawnEnemyAsteroid() {
        int x = Gdx.graphics.getWidth();
        int y = MathUtils.random(0, Gdx.graphics.getHeight());
        Enemy enemy = new EnemyAsteroid(new Vector2(x, y), "stone");
        enemies.add(enemy);

        lastEnemyAsteroid = TimeUtils.nanoTime();
    }


    private void handleCollisions() {
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);

            // Colisiones entre el enemigo y el jugador
            if (enemy.rect.overlaps(player.rect)) {
                if (enemy instanceof EnemyAsteroid){
                    player.lives = 0;
                    pause = true;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                        }
                    }, 2);
                } else {
                    player.lives--;

                    if (player.lives == 0) {
                        pause = true;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                            }
                        }, 2);
                    }
                }
                enemies.removeIndex(i);
                continue;
            }
            // Colisiones entre el enemigo y las balas
            for (int j = player.bullets.size - 1; j >= 0; j--) {
                Bullet bullet = player.bullets.get(j);
                if (bullet.rect.overlaps(enemy.rect)) {
                    // Verificamos si el enemigo es un asteroide
                    if (enemy instanceof EnemyAsteroid) {
                        // Si es un asteroide, solo eliminamos la bala
                        player.bullets.removeIndex(j);
                    } else {
                        // Si no es un asteroide, eliminamos al enemigo y la bala
                        enemies.removeIndex(i);
                        player.bullets.removeIndex(j);
                        player.score++;
                    }
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

            // Verificar si el score alcanza 15 pasamos de nivel
            if (player.score >= nextLevelScore  && !levelChanged) {
                level++;
                background = ResourceManager.getTexture("farback2");
                levelChanged = true;
                /*pause = true;
                //TODO: spawnear enemigo final aqui.
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                    }
                }, 2); // 2 segundos de delay antes de regresar al menú principal*/
            }
        }
        handleGameScreenInput();
    }

    @Override
    public void dispose() {
        player.dispose();
    }
}
