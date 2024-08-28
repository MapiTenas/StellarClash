package com.svalero.stellarclash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MainMenuScreen implements Screen {
    private Stage stage;
    Texture backgroundTexture;
    private SpriteBatch spriteBatch;



    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();
        stage = new Stage();
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("backgroundmainmenu.png"); // Cargar la textura del fondo


        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        VisTextButton playButton = new VisTextButton("JUGAR");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a jugar
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        VisTextButton instructionButton = new VisTextButton("INSTRUCCIONES");
        instructionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de instrucciones
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new InstructionScreen());

            }
        });

        VisTextButton configButton = new VisTextButton("CONFIGURACIÓN");
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de configuración
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreferencesScreen());

            }
        });

        VisTextButton quitButton = new VisTextButton("SALIR");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                System.exit(0);
                // Salir del juego
            }
        });

        VisLabel aboutLabel = new VisLabel("          Mapi Tenas 2024");

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(playButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(configButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(instructionButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(quitButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(aboutLabel).left().width(200).height(20).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        // Pinta la UI en la pantalla. Dt es deltatime, para los fps
        stage.act(dt);
        stage.draw();
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
        stage.dispose();
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
