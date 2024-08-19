package com.svalero.stellarclash.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

public class ResourceManager {

    private static AssetManager assetManager = new AssetManager();

    //Es el que nos dice si están o no los recursos guardados. Se ejecuta de forma asincrona
    public static boolean update(){
        return assetManager.update();
    }

    public static void loadAllResources(){
        assetManager.load("stellarclash.atlas", TextureAtlas.class);
        loadAllSounds();
    }

    //Para conseguir una animación completa
    public static Array<TextureAtlas.AtlasRegion> getAnimation(String name){
        return assetManager.get("stellarclash.atlas", TextureAtlas.class).findRegions(name);
    }
    //Esto es para una sola imagen
    public static TextureRegion getTexture(String name){
        return assetManager.get("stellarclash.atlas", TextureAtlas.class).findRegion(name);
    }

    //Sirve para pre-cargar todos los sonidos que queremos tener en el juego
    private static void loadAllSounds(){
        assetManager.load("sounds" + File.separator + "maintheme.wav", Sound.class);
        assetManager.load("sounds" + File.separator + "pistola.wav", Sound.class);
        assetManager.load("sounds" + File.separator + "explosion.wav", Sound.class);
        assetManager.load("sounds" + File.separator + "bossmusic.wav", Sound.class);
        assetManager.load("sounds" + File.separator + "gameover.wav", Sound.class);
        assetManager.load("sounds" + File.separator + "buzz.wav", Sound.class);
        assetManager.load("sounds" + File.separator + "tada.wav", Sound.class);


    }

    //Sirve para recuperar un sonido concreto
    public static Sound getSound(String name){
        return assetManager.get("sounds" + File.separator + name + ".wav");
    }

}
