package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.actor.NPCActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.BadAIController;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.map.GameMap;

public class NeweyJrpg extends ApplicationAdapter {
	GameActor chara;
	NPCActor npc;
	InputController input;
	BadAIController ai;
	GameMap map;
	Camera camera;
	float stateTime;
	BitmapFont font;
	
	GameScene scene;
	
	float focusX, focusY;
	
	@Override
	public void create () {
		//GdxNativesLoader.load(); 
		
		camera = new PerspectiveCamera();

		map = new GameMap("dungeon.png", "maps/map1.txt");
		
		InputController input = new InputController();
		Gdx.input.setInputProcessor(input);
		
		chara = new GameActor(new Texture("hero.png"), 0, 200f, 200f);
		chara.setController(input);
		
		npc = new NPCActor(new Texture("hero.png"), 0, 120f, 40f);
		//npc.setController(new BadAIController());
		
		scene = new GameScene(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera), new SpriteBatch(), chara, map);
		
		scene.addActor(npc);
		
		font = new BitmapFont();

		stateTime = 0f;
		System.out.println("Create method done");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		stateTime += deltaTime;
				
		scene.getBatch().begin();
		scene.draw(stateTime);
		
		font.draw(scene.getBatch(), "Chara pos: " + chara.getX() + ", " + chara.getY(), 0, 220);
		font.draw(scene.getBatch(), "Focus: " + scene.focusX + ", " + scene.focusY, 0, 240);

		scene.getBatch().end();
		
		scene.act(stateTime);
		
		
		//npc.setPositionToBody();
	}
	
	@Override
	public void resize(int width, int height) {
		scene.getViewport().update(width, height);
	}
}
