package com.svalero.stellarclash.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character{
    public int score;
    public int lives;

    public Player(Texture texture){
        super(texture, new Vector2(0,0));
        lives = 3;
    }

    //Constructor que hereda de la clase padre.
    public Player (Texture texture, Vector2 position){
        super(texture,position);
        lives = 3;
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
    }
}
