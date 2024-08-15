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
import com.svalero.stellarclash.screen.GameOverScreen;
import com.svalero.stellarclash.screen.MainMenuScreen;
import com.svalero.stellarclash.screen.VictoryScreen;

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
    EnemyFinalBoss finalBoss;
    boolean bossMovingUp;

    public SpriteManager(){
        initialize();
    }

    private void initialize(){
        player = new Player(new Vector2(0,0),"player");
        pause = false;
        background = ResourceManager.getTexture("farback");
        level = 1;
        levelChanged = false;
        nextLevelScore = 5; //Puntuación para pasar al siguiente nivel //Todo: Modificar para que segun el nivel de dificultad escogido cambie
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
    //Metodo para crear naves enemigas
    private void spawnShipEnemy(){
        int x = Gdx.graphics.getWidth();
        int y = MathUtils.random(0, Gdx.graphics.getHeight());
        Enemy enemy = new EnemyShip(new Vector2(x, y), "enemy");
        enemies.add(enemy);

        lastEnemyShip = TimeUtils.nanoTime();

    }
    //Metodo para crear asteroides
    private void spawnEnemyAsteroid() {
        int x = Gdx.graphics.getWidth();
        int y = MathUtils.random(0, Gdx.graphics.getHeight());
        Enemy enemy = new EnemyAsteroid(new Vector2(x, y), "stone");
        enemies.add(enemy);

        lastEnemyAsteroid = TimeUtils.nanoTime();
    }

    // Método para crear el jefe final
    private void spawnFinalBoss() {
        int x = Gdx.graphics.getWidth() - 200; // Aparece cerca del borde derecho de la pantalla
        int y = Gdx.graphics.getHeight() / 2; // Aparece en el centro vertical de la pantalla
        finalBoss = new EnemyFinalBoss(new Vector2(x, y), "finalboss");
        bossMovingUp = true;
    }

    // Método para actualizar el movimiento del jefe final
    private void updateFinalBoss() {
        float speed = 100 * Gdx.graphics.getDeltaTime(); // Velocidad del jefe

        // Movimiento de arriba hacia abajo y viceversa
        if (bossMovingUp) {
            finalBoss.move(0, speed); // Mover hacia arriba
            if (finalBoss.position.y >= Gdx.graphics.getHeight() - finalBoss.rect.height) {
                bossMovingUp = false; // Cambiar dirección hacia abajo
            }
        } else {
            finalBoss.move(0, -speed); // Mover hacia abajo
            if (finalBoss.position.y <= 0) {
                bossMovingUp = true; // Cambiar dirección hacia arriba
            }
        }
        // Gestionar disparos automáticos
        finalBoss.tryToShoot();

        // Actualizar balas del jefe final
        finalBoss.updateBullets(Gdx.graphics.getDeltaTime());
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
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
                        }
                    }, 2);
                } else {
                    player.lives--;

                    if (player.lives == 0) {
                        pause = true;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
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

    private void handleBossCollisions() {
        if (finalBoss == null) {
            return; // Si finalBoss es null, salimos del método
        }

        for (int j = player.bullets.size - 1; j >= 0; j--) {
            Bullet bullet = player.bullets.get(j);
            if (bullet.rect.overlaps(finalBoss.rect)) {
                finalBoss.lives--; // Resto una vida al jefe
                player.bullets.removeIndex(j); // Elimino la bala

                if (finalBoss.lives <= 0) {
                    // El jefe final muere
                    finalBoss = null;
                    // Eliminar todas las balas del jefe final
                    if (finalBoss != null) {
                        finalBoss.bullets.clear(); // Vaciar la lista de balas del jefe
                    }
                    pause = true; // Pausar el juego para evitar más actualizaciones
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new VictoryScreen());
                        }
                    }, 2);
                }
                break;
            }
        }

        // Colisiones con balas del jefe final
        if (finalBoss != null) { // Verificar de nuevo si finalBoss no es null antes de acceder a las balas
            for (int j = finalBoss.bullets.size - 1; j >= 0; j--) {
                EnemyBullet bullet = finalBoss.bullets.get(j);
                if (bullet.rect.overlaps(player.rect)) {
                    player.lives--;
                    finalBoss.bullets.removeIndex(j);

                    if (player.lives <= 0) {
                        // Si el boss mata al jugador con las balas
                        pause = true;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
                            }
                        }, 2);
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
                spawnFinalBoss(); // Llamar al método para crear el jefe final
            }
            if (finalBoss != null) {
                updateFinalBoss();
                handleBossCollisions();
            }
        }
        handleGameScreenInput();
    }

    @Override
    public void dispose() {
        player.dispose();
    }
}
