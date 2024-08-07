package com.svalero.stellarclash.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class ResourceManager {

    private static AssetManager assetManager = new AssetManager();

    //Es el que nos dice si están o no los recursos guardados. Se ejecuta de forma asincrona
    public static boolean update(){
        return assetManager.update();
    }

    public static void loadAllResources(){
        assetManager.load("stellarclash.atlas", TextureAtlas.class);
    }

    //Para conseguir una animación completa
    public static Array<TextureAtlas.AtlasRegion> getAnimation(String name){
        return assetManager.get("stellarclash.atlas", TextureAtlas.class).findRegions(name);
    }

}
