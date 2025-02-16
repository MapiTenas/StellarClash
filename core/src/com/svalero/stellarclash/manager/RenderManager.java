package com.svalero.stellarclash.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.stellarclash.domain.Bullet;
import com.svalero.stellarclash.domain.Enemy;
import com.svalero.stellarclash.domain.EnemyBullet;
import com.svalero.stellarclash.domain.PowerUp;

public class RenderManager implements Disposable {
    //Se encarga de pintar las cosas en pantalla
    SpriteBatch batch;
    BitmapFont font;
    SpriteManager spriteManager;
    //ShapeRenderer shapeRenderer;

    public RenderManager(SpriteManager spriteManager){
        this.spriteManager = spriteManager;
        initialize();
    }

    private void initialize(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        //shapeRenderer = new ShapeRenderer();
    }
    //Dibujar el fondo
    private void drawBackground() {
        TextureRegion background = spriteManager.getCurrentBackground();
        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    //Dibujar al jugador
    private void drawPlayer(){
        spriteManager.player.draw(batch);
        spriteManager.player.drawBullets(batch);
    }

    private void drawEnemies(){
        for (Enemy enemy : spriteManager.enemies) {
            enemy.draw(batch);
        }
    }

    // Método para dibujar las balas del jefe final
    public void drawBossBullets(SpriteBatch batch) {
        if (spriteManager.finalBoss != null) {
            for (EnemyBullet bullet : spriteManager.finalBoss.bullets) {
                bullet.draw(batch);
            }
        }
    }

    private void drawPowerUps() {
        for (PowerUp powerUp : spriteManager.powerUps) {
            powerUp.draw(batch);
        }
    }

    private void drawHud(){
        font.draw(batch, "Vidas: " + spriteManager.player.lives, 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Enemigos eliminados: " + spriteManager.player.score, 200, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Nivel: " + spriteManager.level, 600, Gdx.graphics.getHeight() - 20);

        font.getData().setScale(2.0f); // Cambia el valor a lo que prefieras para ajustar el tamaño de la fuente
    }

    //Para visualizar los rectangulos de colisión!
    /*private void drawCollisionRects(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Rojo para el jugador
        shapeRenderer.rect(spriteManager.player.rect.x, spriteManager.player.rect.y, spriteManager.player.rect.width, spriteManager.player.rect.height);
        shapeRenderer.setColor(0, 1, 0, 1); // Verde para los enemigos
        for (Enemy enemy : spriteManager.enemies) {
            shapeRenderer.rect(enemy.rect.x, enemy.rect.y, enemy.rect.width, enemy.rect.height);
        }
        shapeRenderer.setColor(0, 0, 1, 1); //Azul para las balas
        for (Bullet bullet : spriteManager.player.bullets) {
            shapeRenderer.rect(bullet.rect.x, bullet.rect.y, bullet.rect.width, bullet.rect.height);
        }
        if (spriteManager.finalBoss != null) {
            shapeRenderer.setColor(1, 1, 0, 1); // Amarillo para el jefe final
            shapeRenderer.rect(spriteManager.finalBoss.rect.x, spriteManager.finalBoss.rect.y, spriteManager.finalBoss.rect.width, spriteManager.finalBoss.rect.height);
            shapeRenderer.setColor(1, 0, 1, 1); // Morado para las balas del jefe final
            for (EnemyBullet bullet : spriteManager.finalBoss.bullets) {
                shapeRenderer.rect(bullet.rect.x, bullet.rect.y, bullet.rect.width, bullet.rect.height);
            }
        }
        shapeRenderer.end();
    }*/

    public void draw(){
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        drawBackground();
        drawPlayer();
        drawEnemies();
        drawPowerUps();
        if (spriteManager.finalBoss != null) {
            spriteManager.finalBoss.draw(batch);
            drawBossBullets(batch);

        }
        drawHud();
        batch.end();
        //drawCollisionRects();
    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        //shapeRenderer.dispose();
    }
}
