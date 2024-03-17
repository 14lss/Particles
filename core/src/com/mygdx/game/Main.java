package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	FPSLogger fpsLogger;

	final int screenWidth = 800;
	final int screenHeight = 800;

	final int particleCount = 5000;
	Particle[] particles = new Particle[particleCount];
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		fpsLogger = new FPSLogger();

		for (int i = 0; i < particleCount; i++) {
			particles[i] = new Particle(screenWidth, screenHeight);
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		for (int i = 0; i < particleCount; i++) {
			particles[i].attract(mousePos, 1);
			particles[i].doFriction(0.99f);
			particles[i].move(screenWidth, screenHeight);

			particles[i].draw(shapeRenderer);
		}
		fpsLogger.log();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
