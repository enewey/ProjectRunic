package com.neweyjrpg.game;

import java.util.LinkedList;
import java.util.ListIterator;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.interfaces.Interaction;
import com.neweyjrpg.manager.ActorManager;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.manager.WindowManager;
import com.neweyjrpg.map.GameMap;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class GameScene extends InputAdapter implements IProducesInputs, IHandlesInteraction {
	
	//CHANGE TO PRIVATE
	public float scrollX, scrollY; //Camera focus
	public float maxScrollX, maxScrollY;
	
	private Viewport viewport; //Used for drawing to the screen
	public Viewport getViewport() {	return viewport; }

	private Batch batch; //Used for drawing sprites
	public Batch getBatch() { return batch; }
	
	private GameMap map;
	public GameMap getMap() { return map; }
	public void setMap(GameMap map) { this.map = map; }
	
	private ActorManager actorManager;
	private WindowManager windowManager;
	private LinkedList<Manager> managers;

	private InputController inputController; //Used to relay inputs to the actor
	
	public GameScene(Viewport viewport, Batch batch, CharacterActor playerActor, GameMap map) {
		this.batch = batch;
		this.viewport = viewport;
		this.inputController = new InputController();
		
		this.map = map;
		this.scrollX = 0f;
		this.scrollY = 0f;
		//Max scroll dictates how far the camera can scroll, so the edges align to the edge of the GameMap 
		this.maxScrollX = -((map.getDimX() * Constants.TILE_WIDTH)-Constants.GAME_WIDTH);
		this.maxScrollY = -((map.getDimY() * Constants.TILE_HEIGHT)-Constants.GAME_HEIGHT);
		
		this.actorManager = new ActorManager(playerActor, map.getDimX()*Constants.TILE_WIDTH, map.getDimY()*Constants.TILE_HEIGHT, this, this);
		this.windowManager = new WindowManager(true);
		
		this.managers = new LinkedList<Manager>();
		managers.addLast(actorManager);
		managers.addLast(windowManager);
	}
	
	public void addActor(GameActor actor) {
		actorManager.addActor(actor);
	}
	
	public void setPlayer(CharacterActor actor) {
		actor.setController(this);
		this.actorManager.setPlayer(actor);
	}
	
	public void draw(float deltaTime) {		
		if (!batch.isDrawing()) //If the batch has not begun, go ahead and begin it
			batch.begin();
		
		//Draw each tile from the map -- TODO: draw only tiles within the viewport!
		for (int x=0; x<map.getDimX(); x++)
			for (int y=0; y<map.getDimY(); y++)
				batch.draw(map.getMapTile(x, y).getGraphic(), scrollX+(x*16), scrollY+(y*16));
		
		for (Manager m : managers) {
			m.draw(deltaTime, scrollX, scrollY, batch);
		}
	}
	
	/**
	 * 	Calls each actor's 'act' method, and then immediately checks for collision
	 * @param deltaTime
	 */
	public void act(float deltaTime) {
		buttonPressing();
		
		ListIterator<Manager> li = managers.listIterator(managers.size());
		while (li.hasPrevious()) {
			if (li.previous().act(deltaTime)) {
				break;
			}
		}
		
		this.adjustFocus();
	}
	
	private void buttonPressing() {
		while (!this.inputController.getQueue().isEmpty())
		{
			int pop = InputController.getButton(this.inputController.getQueue().pop());
			ListIterator<Manager> li = managers.listIterator(managers.size());
			while (li.hasPrevious()) {
				if (li.previous().handleButtonPress(pop)) {
					break;
				}
			}
		}
	}
	
	/**
	 * Sets the camera to be focused on the player.
	 */
	public void adjustFocus() {
		Vector2 pos = actorManager.getPlayerPos();
		
		if (pos.x+scrollX > Constants.UPPER_BOUND_X) {
			scrollX = Math.max(Math.round(Constants.UPPER_BOUND_X - pos.x), maxScrollX);
		} else if (pos.x+scrollX < Constants.LOWER_BOUND_X) {
			scrollX = Math.min(Math.round(Constants.LOWER_BOUND_X - pos.x), 0);
		}
		
		if (pos.y+scrollY > Constants.UPPER_BOUND_Y) {
			scrollY = Math.max(Math.round(Constants.UPPER_BOUND_Y - pos.y), maxScrollY);
		} else if (pos.y+scrollY < Constants.LOWER_BOUND_Y) {
			scrollY = Math.min(Math.round(Constants.LOWER_BOUND_Y - pos.y), 0);
		}
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (inputController != null)
			return inputController.keyUp(keycode);
		return super.keyUp(keycode);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (inputController != null)
			return inputController.keyDown(keycode);
		return super.keyDown(keycode);
	}
	
	@Override
	public DirectionalInput getDirectionalInput() {
		if (inputController != null)
			return inputController.getDirectionalInput();
		return null;
	}
	
	@Override
	public ButtonInput getButtonInput() {
		if (inputController != null)
			return inputController.getButtonInput();
		return null;
	}
	
	@Override
	public boolean handle(Interaction interaction) {
		if (interaction == null) return false;
		
		for (Manager m : managers){
			if (m.handle(interaction)){
				return true;
			}
		}
		return false;
	}
}
