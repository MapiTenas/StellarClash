package com.svalero.stellarclash;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.svalero.stellarclash.StellarClash;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("StellarClash");
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode()); //  Pantalla completa
		new Lwjgl3Application(new StellarClash(), config);
	}
}
