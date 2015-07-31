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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.actor.NPCActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.BadAIController;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.listener.MovementInputListener;
import com.neweyjrpg.map.GameMap;

public class NeweyJrpg extends ApplicationAdapter {
	GameActor chara;
	NPCActor npc;
	InputController input;
	BadAIController ai;
	GameMap map;
	Camera camera;
	float stateTime;
	
	Stage stage;
	
	BitmapFont font;
	
	@Override
	public void create () {
		//GdxNativesLoader.load(); 
		
		camera = new PerspectiveCamera();
		stage = new Stage(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera), new SpriteBatch());
		
		map = new GameMap("dungeon.png", "maps/map1.txt");
		System.out.println("Creating actors");
		chara = new GameActor(new Texture("hero.png"), 0, 40f, 40f);
		chara.addListener(new MovementInputListener());
		
		npc = new NPCActor(new Texture("hero.png"), 0, 120f, 40f);
		ai = new BadAIController();
		npc.setAi(ai);
		
		
		stage.addActor(chara);
		stage.setKeyboardFocus(chara);
		stage.addActor(npc);
		
		font = new BitmapFont();

		Gdx.input.setInputProcessor(stage);
		stateTime = 0f;
		System.out.println("Create method done");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		stateTime += deltaTime;
		
		Batch batch = stage.getBatch();
		batch.begin();
		for (int x=0; x<30; x++)
			for (int y=0; y<30; y++)
				batch.draw(map.getMapTile(x, y).getGraphic(), x*16, y*16);
		
		font.draw(batch, "Chara pos: " + chara.getX() + ", " + chara.getY(), 0, 220);

		for (Actor actor : stage.getActors()){
			actor.draw(batch, stateTime);
		}
		batch.end();
		
		//Handle actor movement
		//chara.moveFromInput(input.getDirs());
		//npc.moveFromInput(ai.getDirs());
		
		stage.act();
		//npc.setPositionToBody();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
}
