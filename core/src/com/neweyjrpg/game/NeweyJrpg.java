package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.actor.NPCActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.BadAIController;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.map.GameMap;

public class NeweyJrpg extends ApplicationAdapter {
	SpriteBatch batch;
	GameActor chara;
	NPCActor npc;
	InputController input;
	BadAIController ai;
	
	GameMap map;
	
	Camera camera;
	Viewport viewport;
	
	float stateTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new GameMap("dungeon.png", "maps/map1.txt");

		chara = new GameActor(new Texture("hero.png"), 0, 40f, 40f);
		npc = new NPCActor(new Texture("hero.png"), 0, 120f, 40f);
		input = new InputController();
		ai = new BadAIController();
		
		camera = new PerspectiveCamera();
		viewport = new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera);
		
		stateTime = 0f;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.input.setInputProcessor(input);
		batch.begin();
		for (int x=0; x<30; x++)
			for (int y=0; y<30; y++)
				batch.draw(map.getMapTile(x, y).getGraphic(), x*16, y*16);
		
		//Handle actor movement
		chara.moveFromInput(input.getDirectionalInput());
		boolean[] inputs = ai.getDirectionalInput();
		npc.moveFromInput(inputs);
		
		stateTime += Gdx.graphics.getDeltaTime();
		
		chara.draw(batch, stateTime);
		npc.draw(batch, stateTime);
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
