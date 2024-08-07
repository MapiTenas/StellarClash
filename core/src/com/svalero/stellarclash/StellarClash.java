package com.svalero.stellarclash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.svalero.stellarclash.screen.MainMenuScreen;

public class StellarClash extends Game {

	@Override
	public void create () {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
