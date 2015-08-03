package com.neweyjrpg.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.map.GameMap;

public class GameScene {
	
	//CHANGE TO PRIVATE
	public float scrollX, scrollY; //Camera focus
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
		this.actors.add(playerActor);
		this.setPlayer(playerActor);
		this.map = map;
		
		this.scrollX = 0f;
		this.scrollY = 0f;
		
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
				batch.draw(map.getMapTile(x, y).getGraphic(), scrollX+(x*16), scrollY+(y*16));
		
//		this.player.draw(batch, deltaTime, player.getX() + scrollX, player.getY() + scrollY);
		for (GameActor actor : actors) {
			actor.draw(batch, deltaTime, actor.getX() + scrollX, actor.getY() + scrollY);
		}
	}
	
	public void act(float deltaTime) {
//		this.player.act(deltaTime);
		for (GameActor actor : actors){
			actor.act(deltaTime);
		}
		this.detectCollision();
		this.sortActors();
		this.adjustFocus();
	}
	
	private void detectCollision() {
		for (int i = 0; i < actors.size; i++) {
			for (int j = 0; j < actors.size; j++) {
				if (i == j) {
					continue;
				}
				GameActor subject = actors.get(j);
				if (subject.getPhysicsModel().getBounds().x < 0)
					subject.setPosition(0, subject.getY());
				else if (subject.getPhysicsModel().getBounds().x > map.getDimX()*Constants.TILE_WIDTH - subject.getPhysicsModel().getBounds().width)
					subject.setPosition(map.getDimX()*Constants.TILE_WIDTH - subject.getPhysicsModel().getBounds().width, subject.getY());
				
				if (subject.getPhysicsModel().getBounds().y < 0)
					subject.setPosition(subject.getX(), 0);
				else if (subject.getPhysicsModel().getBounds().y > map.getDimY()*Constants.TILE_HEIGHT - subject.getPhysicsModel().getBounds().height)
					subject.setPosition(subject.getX(), map.getDimY()*Constants.TILE_HEIGHT - subject.getPhysicsModel().getBounds().height);
				
				GameActor actor = actors.get(i);
				if (actor.getX() == actor.getOldPosition().x && actor.getY() == actor.getOldPosition().y)
					continue;
				
				float currX = actor.getX();
				float currY = actor.getY();
				float oldX = actor.getOldPosition().x;
				float oldY = actor.getOldPosition().y;
				if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())){
					//TODO check body types here
					actor.setPosition(currX, oldY);
					if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())){
						actor.setPosition(oldX, currY);
						if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())){
							actor.setPosition(oldX, oldY);
						}
					}
				}
			}
		}
	}
	
	private void sortActors() {
		actors.sort();
	}
	
	public void adjustFocus() {
		if (player.getX()+scrollX > Constants.UPPER_BOUND_X) {
			scrollX = Math.max(Constants.UPPER_BOUND_X - player.getX(), maxScrollX);
		} else if (player.getX()+scrollX < Constants.LOWER_BOUND_X) {
			scrollX = Math.min(Constants.LOWER_BOUND_X - player.getX(), 0);
		}
		
		if (player.getY()+scrollY > Constants.UPPER_BOUND_Y) {
			scrollY = Math.max(Constants.UPPER_BOUND_Y - player.getY(), maxScrollY);
		} else if (player.getY()+scrollY < Constants.LOWER_BOUND_Y) {
			scrollY = Math.min(Constants.LOWER_BOUND_Y - player.getY(), 0);
		}
	}

}
