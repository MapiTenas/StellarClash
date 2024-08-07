package com.svalero.stellarclash.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Character implements Disposable {
    public Texture texture;
    public Vector2 position;
    public Rectangle rect;


    //Constructor básico, como minimo cada personaje necesita una imagen y una posición.
    public Character(Texture texture, Vector2 position){
        this.texture = texture;
        this.position = position;
        rect = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    //Método para dibujar en pantalla los characters
    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
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
