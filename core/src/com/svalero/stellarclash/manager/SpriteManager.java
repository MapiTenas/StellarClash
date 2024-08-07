package com.svalero.stellarclash.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.stellarclash.domain.Player;
import com.svalero.stellarclash.screen.MainMenuScreen;

public class SpriteManager implements Disposable {
    //Se encarga de la lógica
    Player player;
    boolean pause;

    public SpriteManager(){
        initialize();
    }

    private void initialize(){
        player = new Player(new Vector2(0,0),"player");
        pause = false;
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
            player.manageInput();
        }
        handleGameScreenInput();
    }

    @Override
    public void dispose() {
        player.dispose();
    }
}
