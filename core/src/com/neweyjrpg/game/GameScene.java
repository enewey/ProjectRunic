package com.neweyjrpg.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.map.GameMap;

public class GameScene {
	
	//CHANGE TO PRIVATE
	public float focusX, focusY; //Camera focus
	public float maxScrollX, maxScrollY;
	
	private Viewport viewport;
	public Viewport getViewport() {	return viewport; }

	private Batch batch;
	public Batch getBatch() { return batch; }
	
	private GameMap map;
	public GameMap getMap() { return map; }
	public void setMap(GameMap map) { this.map = map; }

	private Array<GameActor> actors;
	public Array<GameActor> getActors() { return actors; }
	
	private GameActor player;
	
	public GameScene(Viewport viewport, Batch batch, GameActor playerActor, GameMap map) {
		this.batch = batch;
		this.viewport = viewport;
		this.actors = new Array<GameActor>(false, 10, GameActor.class);
		this.setPlayer(playerActor);
		this.map = map;
		
		this.focusX = 0f;
		this.focusY = 0f;
		
		this.maxScrollX = -((map.getDimX() * Constants.TILE_WIDTH)-Constants.GAME_WIDTH);
		this.maxScrollY = -((map.getDimY() * Constants.TILE_HEIGHT)-Constants.GAME_HEIGHT);
	}
	
	public void addActor(GameActor actor) {
		actors.add(actor);
	}
	
	public void setPlayer(GameActor actor) {
		this.player = actor;
	}
	
	public void draw(float deltaTime) {
		if (!batch.isDrawing())
			batch.begin();
		
		for (int x=0; x<map.getDimX(); x++)
			for (int y=0; y<map.getDimY(); y++)
				batch.draw(map.getMapTile(x, y).getGraphic(), focusX+(x*16), focusY+(y*16));
		
		this.player.draw(batch, deltaTime, player.getX() + focusX, player.getY() + focusY);
		for (GameActor actor : actors) {
			actor.draw(batch, deltaTime, actor.getX() + focusX, actor.getY() + focusY);
		}
	}
	
	public void act(float deltaTime) {
		this.player.act(deltaTime);
		for (GameActor actor : actors){
			actor.act(deltaTime);
		}
		
		this.adjustFocus();
	}
	
	public void adjustFocus() {
		if (player.getX()+focusX > Constants.UPPER_BOUND_X) {
			focusX = Math.max(Constants.UPPER_BOUND_X - player.getX(), maxScrollX);
		} else if (player.getX()+focusX < Constants.LOWER_BOUND_X) {
			focusX = Math.min(Constants.LOWER_BOUND_X - player.getX(), 0);
		}
		
		if (player.getY()+focusY > Constants.UPPER_BOUND_Y) {
			focusY = Math.max(Constants.UPPER_BOUND_Y - player.getY(), maxScrollY);
		} else if (player.getY()+focusY < Constants.LOWER_BOUND_Y) {
			focusY = Math.min(Constants.LOWER_BOUND_Y - player.getY(), 0);
		}
	}

}
