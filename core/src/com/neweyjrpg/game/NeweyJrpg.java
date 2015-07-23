package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.graphic.ActorGraphic;
import com.neweyjrpg.graphic.TileGraphic;
import com.neweyjrpg.map.GameMap;
import com.neweyjrpg.map.MapParser;

public class NeweyJrpg extends ApplicationAdapter {
	SpriteBatch batch;
	GameActor chara;
	InputController input;
	
	GameMap map;
	
	Camera camera;
	Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		MapParser mapParser = new MapParser("maps/map1.txt");
		map = new GameMap("dungeon.png", mapParser.getMapData());
		
		
		//tile = new TileGraphic(new Texture("dungeon.png"), 20, 10);
		chara = new GameActor(
					new Sprite(new ActorGraphic(new Texture("charas.png"), 1, 0)),
					new Sprite(new ActorGraphic(new Texture("charas.png"), 13, 0)),
					40f, 40f);
		input = new InputController();
		
		camera = new PerspectiveCamera();
		viewport = new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera);	
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.input.setInputProcessor(input);
		batch.begin();
		for (int x=0; x<30; x++)
			for (int y=0; y<30; y++)
				batch.draw(map.getMapTile(x, y).getGraphic(), x*16, y*16);
		
		boolean[] dirs = input.getInputs();
		float tx=0f, ty=0f;
		if (dirs[0] && !dirs[2])
			ty=2f;
		else if (!dirs[0] && dirs[2])
			ty=-2f;
		if (dirs[1] && !dirs[3])
			tx=2f;
		else if (!dirs[1] && dirs[3])
			tx=-2f;
		chara.move(tx, ty);
		
		chara.draw(batch);
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
