package com.neweyjrpg.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.GhostActor;
import com.neweyjrpg.actor.MassiveActor;
import com.neweyjrpg.actor.NPCActor;
import com.neweyjrpg.actor.PlayerActor;
import com.neweyjrpg.actor.StaticActor;
import com.neweyjrpg.collider.BlockingCollider;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.BadAIController;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.graphic.TileGraphic;
import com.neweyjrpg.interaction.MessageInteraction;
import com.neweyjrpg.interaction.MovementInteraction;
import com.neweyjrpg.map.GameMap;
import com.neweyjrpg.physics.BlockBody;

public class NeweyJrpg extends ApplicationAdapter {
	CharacterActor chara;
	GameMap map;
	GameScene scene;
	BitmapFont font;
	Camera camera;
	float focusX, focusY;

	@Override
	public void create() {
		camera = new PerspectiveCamera();
		map = new GameMap("maps/map1.map");
		chara = new PlayerActor(new Texture("hero.png"), 0, 220f, 220f,
				new BlockBody(PhysicalState.MovingBlock,
						new Rectangle(220f, 220f, Constants.CHARA_PHYS_WIDTH, Constants.CHARA_PHYS_HEIGHT)),
				Enums.Priority.Same);
		chara.setName("PLAYER");
		chara.setCollider(new BlockingCollider());
		
		scene = new GameScene(new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera), new SpriteBatch(),
				chara, map);
		Gdx.input.setInputProcessor(scene);
		
		ArrayList<GhostActor> mapBlocks = map.getBlocks();
		for (GhostActor block : mapBlocks) {
			scene.addActor(block);
		}

		addNPCs(scene, 6);
		
		TextureRegion[][] bigBlockGraphics = new TextureRegion[10][10];
		for (int k = 0; k < 10; k++) {
			for (int i = 0; i < 10; i++) {
				bigBlockGraphics[k][i] = new TileGraphic(new Texture("dungeon.png"), 0, 5);
			}
		}
		MassiveActor bigBlock = new MassiveActor(176f, 112f,
				new BlockBody(PhysicalState.StaticBlock, new Rectangle(96f, 96f, 160f, 160f)), bigBlockGraphics, 16f,
				16f, Enums.Priority.Same);
		bigBlock.setCollider(new BlockingCollider());
		bigBlock.setName("BIGBLOCK");
		scene.addActor(bigBlock);

		addStaticBlock(scene, 32f, 32f);
		addStaticBlock(scene, 32f, 16f);
		addStaticBlock(scene, 48f, 32f);
		addStaticBlock(scene, 48f, 16f);
		
		addPushBlock(scene, 128f, 128f);
		addPushBlock(scene, 128f, 156f);
		
		addBlockWithAction(scene, 128f, 210f);
		
		font = new BitmapFont();

		System.out.println("Create method done");
	}
	
	private void addStaticBlock(GameScene s, float x, float y) {
		StaticActor block = new StaticActor(new TileGraphic(new Texture("dungeon.png"), 0, 5), x, y,
				new BlockBody(PhysicalState.StaticBlock,
						new Rectangle(x, y, Constants.TILE_WIDTH, Constants.TILE_HEIGHT)),
				Enums.Priority.Same);
		block.setCollider(new BlockingCollider());
		block.setColor(Color.WHITE);
		block.setName("BLOCK");
		s.addActor(block);
	}
	
	private void addPushBlock(GameScene s, float x, float y) {
		StaticActor block = new StaticActor(new TileGraphic(new Texture("dungeon.png"), 0, 6), x, y,
				new BlockBody(PhysicalState.StaticPushable,
						new Rectangle(x, y, Constants.TILE_WIDTH, Constants.TILE_HEIGHT)),
				Enums.Priority.Same);
		block.setCollider(new BlockingCollider());
		block.setColor(Color.WHITE);
		block.setName("PUSHBLOCK");
		s.addActor(block);
	}
	
	private void addBlockWithAction(GameScene s, float x, float y) {
		StaticActor block = new StaticActor(new TileGraphic(new Texture("dungeon.png"), 0, 6), x, y,
				new BlockBody(PhysicalState.StaticBlock,
						new Rectangle(x, y, Constants.TILE_WIDTH, Constants.TILE_HEIGHT)),
				Enums.Priority.Same);
		block.setCollider(new BlockingCollider());
		block.setColor(Color.WHITE);
		block.setName("ACTIONBLOCK");
		block.addOnActionInteraction(new MovementInteraction("PUSHBLOCK", Enums.Move.StepDir, Enums.Dir.UP, 40f));
		
		s.addActor(block);
		
	}
	
	private void addNPCs(GameScene s, int num) {
		// Bunch of random NPCs
		for (int i = 0; i < num; i++) {
			float x = (int) (Math.random() * 1000) % 300;
			float y = (int) (Math.random() * 1000) % 300;
			NPCActor npc = new NPCActor(new Texture("hero.png"), 0, x, y,
					new BlockBody(PhysicalState.MovingPushable,
							new Rectangle(x, y, Constants.CHARA_PHYS_WIDTH, Constants.CHARA_PHYS_HEIGHT)),
					Enums.Priority.Same);
			npc.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random(),
					(float) Math.min(Math.random() + 0.25, 1.0)));
			npc.setController(new BadAIController());
			npc.setMovespeed((float) (Math.random() + 0.5f) * 1.3f);
			npc.setCollider(new BlockingCollider());
			// npc.setOnTouchInteraction(new MessageInteraction("TOUCH " + i));
			npc.addOnActionInteraction(new MessageInteraction(
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			npc.addOnActionInteraction(new MessageInteraction("Testing a second message!"));
			s.addActor(npc);
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		scene.incrementStateTime(deltaTime);
		// stateTime += deltaTime;

		scene.getBatch().enableBlending();
		scene.getBatch().begin();
		scene.draw(); // Will draw all actors/tiles in the scene

		// Draw overlays here
		font.draw(scene.getBatch(),
				"X/Y: " + chara.getPhysicsModel().getBounds().x + ", " + chara.getPhysicsModel().getBounds().y, 0, 240);

		scene.getBatch().end();

		scene.act();
	}

	@Override
	public void resize(int width, int height) {
		scene.getViewport().update(width, height);
	}

	public void dispose() {
		chara.dispose();
		map.dispose();
		font.dispose();
		scene.dispose();
	}

}
