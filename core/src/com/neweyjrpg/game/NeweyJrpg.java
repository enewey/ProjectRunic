package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.NPCActor;
import com.neweyjrpg.actor.StaticActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.BadAIController;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.graphic.TileGraphic;
import com.neweyjrpg.map.GameMap;
import com.neweyjrpg.models.PhysicsModel;

public class NeweyJrpg extends ApplicationAdapter {
	CharacterActor chara;
	NPCActor npc;
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
		chara = new CharacterActor(new Texture("hero.png"), 0, 220f, 220f, 
				new PhysicsModel(BodyType.DynamicBody, 
						new Rectangle(220f, 220f, 12f, 12f)));
		
		InputController input = new InputController();
		Gdx.input.setInputProcessor(input);
		chara.setController(input);
		
		scene = new GameScene(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera), new SpriteBatch(), chara, map);
		
		//Bunch of random NPCs
		for (int i=0; i<25; i++) {
			float x = (int)(Math.random()*1000)%300;
			float y = (int)(Math.random()*1000)%300;
			npc = new NPCActor(new Texture("hero.png"), 0, x, y,
					new PhysicsModel(BodyType.DynamicBody, 
							new Rectangle(x, y, 12f, 12f)));
			npc.setController(new BadAIController());
			npc.setMovespeed((float)(Math.random()+0.5f)*2.0f);
			scene.addActor(npc);
		}
		for (int k=0; k<10; k++) {
			for (int i=0; i<10; i++) {
				StaticActor a = new StaticActor(new TileGraphic(new Texture("dungeon.png"), 0, 5), 
						48+(i*Constants.TILE_WIDTH), 200-(k*Constants.TILE_HEIGHT),
						new PhysicsModel(BodyType.StaticBody, 
								new Rectangle(48+(i*Constants.TILE_WIDTH), 200, Constants.TILE_WIDTH, Constants.TILE_HEIGHT)));
				
				scene.addActor(a);
			}	
		}
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
		scene.draw(stateTime); //Will draw all actors/tiles in the scene
		
		//Draw overlays here
		font.draw(scene.getBatch(), "Chara: " + chara.getPhysicsModel().getBounds().x + ", " + chara.getPhysicsModel().getBounds().y, 0, 240);

		scene.getBatch().end();
		
		scene.act(stateTime);
	}
	
	@Override
	public void resize(int width, int height) {
		scene.getViewport().update(width, height);
	}
}
