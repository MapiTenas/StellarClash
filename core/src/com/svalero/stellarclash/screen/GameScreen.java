package com.svalero.stellarclash.screen;

import com.badlogic.gdx.Screen;
import com.svalero.stellarclash.manager.RenderManager;
import com.svalero.stellarclash.manager.SpriteManager;

public class GameScreen implements Screen {

    SpriteManager spriteManager;
    RenderManager renderManager;


    @Override
    public void show() {
        spriteManager = new SpriteManager();
        renderManager = new RenderManager(spriteManager);
    }

    @Override
    public void render(float dt) {
        spriteManager.update(dt);
        renderManager.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteManager.dispose();
        renderManager.dispose();
    }
}
