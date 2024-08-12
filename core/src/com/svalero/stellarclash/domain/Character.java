package com.svalero.stellarclash.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.stellarclash.manager.ResourceManager;

public class Character implements Disposable {

    private Animation<TextureRegion> animation;
    public Vector2 position;
    public Rectangle rect;
    private float stateTime;
    private TextureRegion currentFrame;


    //Constructor básico, como minimo cada personaje necesita una imagen y una posición.
    public Character(Vector2 position, String animationName){
        this.position = position;

        animation = new Animation<>(0.15f, ResourceManager.getAnimation(animationName));
        currentFrame = animation.getKeyFrame(0);
        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());

    }

    //Método para dibujar en pantalla los characters
    public void draw(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime(); //Contador de segundos de tiempo jugado

        currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }


    //Metodo para mover tanto la textura como el rect de colisiones
    public void move(float x, float y){
        position.add(x,y);
        rect.x += x;
        rect.y += y;
    }



    @Override
    public void dispose() {

    }
}
