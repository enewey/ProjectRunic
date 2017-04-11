package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.neweyjrpg.actor.StaticActor;
import com.neweyjrpg.actor.characters.EnemyActor;
import com.neweyjrpg.actor.characters.NPCActor;
import com.neweyjrpg.actor.characters.PlayerActor;
import com.neweyjrpg.collider.BlockingCollider;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.constants.Events;
import com.neweyjrpg.controller.BadAIController;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.enums.Enums.SceneAction;
import com.neweyjrpg.graphic.TileGraphic;
import com.neweyjrpg.interaction.SceneInteraction;
import com.neweyjrpg.interaction.actors.DamageInteraction;
import com.neweyjrpg.interaction.actors.DisposeInteraction;
import com.neweyjrpg.interaction.actors.MovementInteraction;
import com.neweyjrpg.interaction.windows.MessageInteraction;
import com.neweyjrpg.interaction.windows.PopupMessageInteraction;
import com.neweyjrpg.physics.BlockBody;

public class NeweyJrpg extends ApplicationAdapter {
	PlayerActor chara;
	String map;
	GameScene scene;
	BitmapFont font;
	float focusX, focusY;

	@Override
	public void create() {
		
		map = "maps/map1.map";
		chara = new PlayerActor(new Texture("hero.png"), 0, 220f, 220f,
				new BlockBody(PhysicalState.MovingBlock,
						new Rectangle(220f, 220f, Constants.CHARA_PHYS_WIDTH, Constants.CHARA_PHYS_HEIGHT)),
				Enums.Priority.Same);
		chara.setName("PLAYER");
		chara.setCollider(new BlockingCollider());
		scene = new GameScene(new NeweyViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT), new SpriteBatch(),
				chara, map);
		Gdx.input.setInputProcessor(scene);
		
		font = Assets.loadFont(Constants.DEFAULT_FONT);
		font.setColor(Color.WHITE);
				
//		TextureRegion[][] bigBlockGraphics = new TextureRegion[10][10];
//		for (int k = 0; k < 10; k++) {
//			for (int i = 0; i < 10; i++) {
//				bigBlockGraphics[k][i] = new TileGraphic(new Texture("dungeon.png"), 0, 5);
//			}
//		}
//		MassiveActor bigBlock = new MassiveActor(176f, 112f,
//				new BlockBody(PhysicalState.StaticBlock, new Rectangle(96f, 96f, 160f, 160f)), bigBlockGraphics, 16f,
//				16f, Enums.Priority.Same);
//		bigBlock.setCollider(new BlockingCollider());
//		bigBlock.setName("BIGBLOCK");
//		scene.addActor(bigBlock);


		addEnemies(scene, 4);
		addNPCs(scene, 4);
		addStaticBlock(scene, 32f, 32f);
		addStaticBlock(scene, 32f, 16f);
		addStaticBlock(scene, 48f, 32f);
		addStaticBlock(scene, 48f, 16f);
		
		addPushBlock(scene, 128f, 234f);
		addPushBlock(scene, 128f, 156f);
		
		addBlockWithAction(scene, 128f, 210f);
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
		block.addOnActionInteraction(new MovementInteraction(scene, "PUSHBLOCK", Enums.Move.StepDir, Enums.Dir.UP, 10f));
		block.addOnActionInteraction(new MovementInteraction(scene, "PUSHBLOCK", Enums.Move.Pause, 0.5f));
		block.addOnActionInteraction(new MovementInteraction(scene, "PUSHBLOCK", Enums.Move.StepDir, Enums.Dir.UP, 30f));
		block.addOnActionInteraction(new MovementInteraction(scene, "PUSHBLOCK", Enums.Move.Pause, 0.5f));
		block.addOnActionInteraction(new MessageInteraction(scene, Constants.DEFAULT_FONT, "Testing a second message!"));
		block.addOnActionInteraction(new SceneInteraction(scene, Enums.SceneAction.ChangeColor, 1.0f, true, 0.0f, 0.0f, 0.0f, 1.0f));
		block.addOnActionInteraction(new SceneInteraction(scene, Enums.SceneAction.ChangeColor, 1.0f, true, 1.0f, 1.0f, 1.0f, 1.0f));
		
		s.addActor(block);
	}
	
	private void addEnemies(GameScene s, int num) {
		// Bunch of random Enemies
		for (int i = 0; i < num; i++) {
			float x = (int) (Math.random() * 1000) % 300;
			float y = (int) (Math.random() * 1000) % 300;
			EnemyActor enemy = new EnemyActor(new Texture("hero.png"), 0, x, y,
					new BlockBody(PhysicalState.MovingPushable,
							new Rectangle(x, y, Constants.CHARA_PHYS_WIDTH, Constants.CHARA_PHYS_HEIGHT)),
					Enums.Priority.Same);
			enemy.setColor(Color.RED);
			enemy.setMovespeed((float) (Math.random() + 0.5f) * 1.3f);
			enemy.setCollider(new BlockingCollider());
			enemy.setController(new BadAIController());
			enemy.setName("Enemy number "+i);
			s.addActor(enemy);
			enemy.addOnTouchInteraction(new DamageInteraction(scene, "PLAYER", enemy));
			enemy.addOnEventInteraction(Events.DEATH, new DisposeInteraction(null, enemy.getName()));
			enemy.addOnEventInteraction(Events.DEATH, new SceneInteraction(null, SceneAction.ChangeColor, 0.1f, false, 1f,0f,0f,1f));
			enemy.addOnEventInteraction(Events.DEATH, new SceneInteraction(null, SceneAction.ChangeColor, 0.1f, false, 1f,1f,1f,1f));
			enemy.addOnEventInteraction(Events.DEATH, new SceneInteraction(null, SceneAction.ChangeColor, 0.1f, false, 1f,0f,0f,1f));
			enemy.addOnEventInteraction(Events.DEATH, new SceneInteraction(null, SceneAction.ChangeColor, 0.1f, false, 1f,1f,1f,1f));
			enemy.addOnEventInteraction(Events.DEATH, new SceneInteraction(null, SceneAction.ChangeColor, 0.1f, false, 1f,0f,0f,1f));
			enemy.addOnEventInteraction(Events.DEATH, new SceneInteraction(null, SceneAction.ChangeColor, 0.1f, false, 1f,1f,1f,1f));
		}
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
			npc.setColor(Color.GREEN);
			npc.setMovespeed((float) (Math.random() + 0.5f) * 1.3f);
			npc.setCollider(new BlockingCollider());
			npc.setController(new BadAIController());
			npc.addOnActionInteraction(new PopupMessageInteraction(scene, "", npc));
			npc.setName("NPC number "+i);
			npc.addOnActionInteraction(new MessageInteraction(scene, Constants.DEFAULT_FONT, 
					"My name is " + npc.getName()));
			npc.addOnActionInteraction(new SceneInteraction(scene, SceneAction.ChangeColor, 1f, true, 0f,0f,0f,1f));
			npc.addOnActionInteraction(new SceneInteraction(scene, SceneAction.ChangeColor, 1f, true, 1f,1f,1f,1f));
			npc.addOnActionInteraction(new SceneInteraction(scene, SceneAction.ChangeColor, 1f, false, 0f,0f,0f,1f));
			npc.addOnActionInteraction(new SceneInteraction(scene, SceneAction.ChangeColor, 1f, false, 1f,1f,1f,1f));
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
		scene.getBatch().setColor(Color.WHITE);
		font.draw(scene.getBatch(), "X/Y: " + chara.getPhysicsBody().getCenter().x + " " + chara.getPhysicsBody().getCenter().y, 0, 15);

		scene.getBatch().end();

		scene.act();
	}

	@Override
	public void resize(int width, int height) {
		
		int modw = (width % Constants.GAME_WIDTH);
		int w = width - modw;
		int modh = (height % Constants.GAME_HEIGHT);
		int h = height - modh;
		scene.getViewport().update(w, h, width, height);
		scene.getViewport().setScreenPosition(modw, modh);
		//scene.getViewport().update(w, h, true);
	}

	public void dispose() {
		chara.dispose();
		font.dispose();
		scene.dispose();
	}

}
