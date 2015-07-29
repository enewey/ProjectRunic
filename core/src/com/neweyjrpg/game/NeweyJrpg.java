package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;
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
	World world;
	float stateTime;
	float accumulator;
	
	@Override
	public void create () {
		GdxNativesLoader.load(); 
		
		batch = new SpriteBatch();
		map = new GameMap("dungeon.png", "maps/map1.txt");
		System.out.println("create world");
		world = new World(new Vector2(0,0), true);
		System.out.println("Creating actors");
		chara = new GameActor(world, new Texture("hero.png"), 0, 40f, 40f);
		npc = new NPCActor(world, new Texture("hero.png"), 0, 120f, 40f);
		
		//charaBody.createFixture(chara.getFixtureDef());
		//npcBody.createFixture(npc.getFixtureDef());
		
		input = new InputController();
		ai = new BadAIController();
		
		camera = new PerspectiveCamera();
		viewport = new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera);
		
		
		stateTime = 0f;
		accumulator = 0f;
		System.out.println("Create method done");
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
		npc.moveFromInput(ai.getDirectionalInput());
		
		stateTime += Gdx.graphics.getDeltaTime();
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body b : bodies){
			
			Object obj = b.getUserData();
			if (obj instanceof GameActor) {
				((GameActor) obj).setPosition(b.getPosition().x, b.getPosition().y);
			}
			
		}
		
		chara.draw(batch, stateTime);
		npc.draw(batch, stateTime);
		batch.end();
		
		doPhysicsStep(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	private void doPhysicsStep(float deltaTime) {
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Constants.TIME_STEP){
			world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
			accumulator -= Constants.TIME_STEP;
		}
		
	}
}
