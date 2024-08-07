package com.svalero.stellarclash.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import static com.svalero.stellarclash.util.Constants.*;

public class ConfigurationManager {

    private static Preferences prefs = Gdx.app.getPreferences(GAME_NAME);

    public static boolean isSoundAlreadyConfigured(){
        return prefs.contains(SOUND);
    }

    //Sonido
    public static boolean isSoundEnabled() {
        return prefs.getBoolean(SOUND);
    }
    public static void enableSound(){
        prefs.putBoolean(SOUND,true);
        prefs.flush();
    }
    public static void disableSound(){
        prefs.putBoolean(SOUND, false);
        prefs.flush();

    }
    public static void switchSound(boolean value){
        prefs.putBoolean(SOUND, value);
        prefs.flush();
    }

    //Dificultad
    public static String getDifficulty(){
        return prefs.getString(DIFFICULTY);
    }

    public static void setDifficulty(String value){
        prefs.putString(DIFFICULTY, value);
        prefs.flush();
    }

}
