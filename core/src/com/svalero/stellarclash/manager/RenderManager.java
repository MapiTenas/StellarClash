package com.svalero.stellarclash.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

public class RenderManager implements Disposable {
    //Se encarga de pintar las cosas en pantalla
    SpriteBatch batch;
    BitmapFont font;
    SpriteManager spriteManager;

    public RenderManager(SpriteManager spriteManager){
        this.spriteManager = spriteManager;
        initialize();
    }

    private void initialize(){
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    //Dibujar al jugador
    private void drawPlayer(){
        spriteManager.player.draw(batch);
    }

    private void drawHud(){
        font.draw(batch, "Vidas: " + spriteManager.player.lives, 20, Gdx.graphics.getHeight() - 20);
        font.getData().setScale(2.0f); // Cambia el valor a lo que prefieras para ajustar el tamaño de la fuente
    }

    public void draw(){
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        drawPlayer();
        drawHud();
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
