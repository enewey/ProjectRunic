package com.neweyjrpg.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.interfaces.Interaction;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;
import com.neweyjrpg.util.ClosestPosition;
import com.neweyjrpg.util.Conversion;

public class ActorManager extends Manager {

	private float boundX, boundY;
	
	private CharacterActor player;
	public CharacterActor getPlayer() { return this.player; }
	public void setPlayer(CharacterActor player) { this.player = player; }
	public Vector2 getPlayerPos() { return new Vector2(player.getX(), player.getY()); }
	
	private Array<GameActor> actors; //All actors in this scene
	public Array<GameActor> getActors() { return actors; }
	public void setActors(Array<GameActor> actors) { this.actors = actors; }
	
	private IHandlesInteraction handler;
	
	public void addActor(GameActor actor) {
		this.actors.add(actor);
	}
	
	public ActorManager(CharacterActor player, float boundX, float boundY, IProducesInputs controller, IHandlesInteraction handler) {
		this.handler = handler;
		this.player = player;
//		this.player.setController(controller);
		this.boundX = boundX;
		this.boundY = boundY;
		this.actors = new Array<GameActor>(false, 10, GameActor.class);
		this.actors.add(this.player);
	}
	
	@Override
	public boolean handleButtonPress(int button) {
		switch(button) {
		case 0:
			return interact();
		case 1:
		case 2:
		case 3:
		default:
			return false;
		}
	}
	
	@Override
	public boolean handleDirectionPress(int button) {
		return false;
	}

	@Override
	public void draw(float deltaTime, float offsetX, float offsetY, Batch batch) {
		//Draw all actors in the scene at their given position, taking into account the camera scrolling
		for (GameActor actor : actors) {
			Vector2 actorSize = actor.getSpriteSize();
			if (actor.getX() + offsetX + actorSize.x < 0 || actor.getX() + offsetX > Constants.GAME_WIDTH + actorSize.x
			 || actor.getY() + offsetY + actorSize.y < 0 || actor.getY() + offsetY > Constants.GAME_HEIGHT + actorSize.y)
				continue;
			
			actor.draw(batch, deltaTime, actor.getX() + offsetX, actor.getY() + offsetY);
		}
	}

	@Override
	public boolean act(float deltaTime) {
		if (player == null || actors == null || actors.size == 0) 
			return false;
		
		player.act(deltaTime);
		this.detectCollision(player, true); //Detect collision after each individual action; this is key
		
		for (GameActor actor : actors){
			if (actor == this.player) continue;
			
			actor.act(deltaTime);
			if (actor.getPhysicsModel().getType() != PhysicalState.StaticBlock
					&& actor.getPhysicsModel().getType() != PhysicalState.StaticPushable) {
				this.detectCollision(actor, false); //Detect collision after each individual action; this is key
			}
		}
		
		return true;
	}

	/**
	 * Checks if a given actor is colliding with any other actors in the scene.
	 * @param actor - the actor that is colliding into another actor.
	 * 			NOTE: Static Actors should not be specified as the parameter for this method.
	 */
	private void detectCollision(GameActor actor, boolean doInteract) {
		if (actor.getPhysicsModel().getBounds().x < 0)
			actor.setPhysicalPosition(0, actor.getPhysicsModel().getBounds().y);
		else if (actor.getPhysicsModel().getBounds().x > boundX - actor.getPhysicsModel().getBounds().width)
			actor.setPhysicalPosition(boundX - actor.getPhysicsModel().getBounds().width, actor.getPhysicsModel().getBounds().y);
		
		if (actor.getPhysicsModel().getBounds().y < 0)
			actor.setPhysicalPosition(actor.getPhysicsModel().getBounds().x, 0);
		else if (actor.getPhysicsModel().getBounds().y > boundY - actor.getPhysicsModel().getBounds().height)
			actor.setPhysicalPosition(actor.getPhysicsModel().getBounds().x, boundY - actor.getPhysicsModel().getBounds().height);
		
		actors.sort();
		Array<GameActor> sortedActors = new Array<GameActor>(actors);
		sortedActors.sort(new ClosestPosition(actor));
		
		for (int j = 0; j < sortedActors.size; j++) {
			GameActor subject = sortedActors.get(j);
			if (subject.equals(actor)) {
				continue;
			}
			if (actor.collideInto(subject) && doInteract) {
				handler.handle(subject.onTouch());
			}
		}
	}
	
	private boolean interact() {
		if (actors == null || actors.size == 0)
			return false;
		
		Array<GameActor> sortedActors = new Array<GameActor>(actors);		
		switch (player.getDir()) {
		case UP:
			for (int i = 0; i<sortedActors.size; i++) {
				if (sortedActors.get(i).getY() < player.getY() || sortedActors.get(i) == player)
					sortedActors.removeIndex(i--);
			}
			break;
		case RIGHT:
			for (int i = 0; i<sortedActors.size; i++) {
				if (sortedActors.get(i).getX() < player.getX() || sortedActors.get(i) == player)
					sortedActors.removeIndex(i--);
			}
			break;
		case LEFT:
			for (int i = 0; i<sortedActors.size; i++) {
				if (sortedActors.get(i).getX() > player.getX() || sortedActors.get(i) == player)
					sortedActors.removeIndex(i--);
			}
			break;
		case DOWN:
			for (int i = 0; i<sortedActors.size; i++) {
				if (sortedActors.get(i).getY() > player.getY() || sortedActors.get(i) == player)
					sortedActors.removeIndex(i--);
			}
			break;
		}
		
		sortedActors.sort(new ClosestPosition(player));
		for (int i=0; /*i<5 &&*/ i<sortedActors.size; i++) {
			if (sortedActors.get(i).onAction() != null && sortedActors.get(i) != null) {
				if (sortedActors.get(i).getDistance(player) < Constants.CHARA_PHYS_SIZE + 2f) {
					handler.handle(sortedActors.get(i).onAction());
				}
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean handle(Interaction interaction) {
		return false;
	}
	
	@Override
	public boolean handleButtonState(ButtonInput button) {
		return false;
	}
	
	@Override
	public boolean handleDirectionState(DirectionalInput dir) {
		this.player.move(Conversion.dirToVec(dir.getInputs()));
		return true;
	}
}
