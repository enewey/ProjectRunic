package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.MassiveActor;
import com.neweyjrpg.actor.NPCActor;
import com.neweyjrpg.actor.PlayerActor;
import com.neweyjrpg.collider.BlockingCollider;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.controller.PatternController;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.graphic.TileGraphic;
import com.neweyjrpg.map.GameMap;
import com.neweyjrpg.models.PhysicsModel;

public class NeweyJrpg extends ApplicationAdapter {
	CharacterActor chara;
	GameMap map;
	Camera camera;
	float stateTime;
	BitmapFont font;
	GameScene scene;
	float focusX, focusY;
	
	@Override
	public void create () {		
		camera = new PerspectiveCamera();
		map = new GameMap("dungeon.png", "maps/map1.txt");
		chara = new PlayerActor(new Texture("hero.png"), 0, 220f, 220f, 
				new PhysicsModel(PhysicalState.MovingBlock, 
						new Rectangle(220f, 220f, 12f, 12f)));
		
		scene = new GameScene(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT,camera), new SpriteBatch(), chara, map);
		Gdx.input.setInputProcessor(scene);
		chara.setCollider(new BlockingCollider());
		
		
		
		//Bunch of random NPCs
		for (int i=0; i<25; i++) {
			float x = (int)(Math.random()*1000)%300;
			float y = (int)(Math.random()*1000)%300;
			NPCActor npc = new NPCActor(new Texture("hero.png"), 0, x, y,
					new PhysicsModel(PhysicalState.MovingPushable, 
							new Rectangle(x, y, 12f, 12f)));
			npc.setController(new PatternController(false));
			npc.setMovespeed((float)(Math.random()+0.5f)*2.0f);
			npc.setCollider(new BlockingCollider());
			scene.addActor(npc);
		}
		TextureRegion[][] bigBlockGraphics = new TextureRegion[10][10];
		for (int k=0; k<10; k++) {
			for (int i=0; i<10; i++) {
				bigBlockGraphics[k][i] = new TileGraphic(new Texture("dungeon.png"), 0, 5);
			}	
		}
		MassiveActor bigBlock = new MassiveActor(48f, 48f, new PhysicsModel(PhysicalState.StaticBlock,
									new Rectangle(48f, 48f, 160f, 160f)), bigBlockGraphics, 16f, 16f);
		bigBlock.setCollider(new BlockingCollider());
		scene.addActor(bigBlock);
		
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
		font.draw(scene.getBatch(), "X/Y: " + chara.getPhysicsModel().getBounds().x + ", " + chara.getPhysicsModel().getBounds().y, 0, 240);

		scene.getBatch().end();
		
		scene.act(stateTime);
	}
	
	@Override
	public void resize(int width, int height) {
		scene.getViewport().update(width, height);
	}
}
