package com.neweyjrpg.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.actor.characters.CharacterActor;
import com.neweyjrpg.actor.characters.PlayerActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interaction.actors.AttackInteraction;
import com.neweyjrpg.interaction.actors.CreateActorInteraction;
import com.neweyjrpg.interaction.actors.DamageInteraction;
import com.neweyjrpg.interaction.actors.DisposeInteraction;
import com.neweyjrpg.interaction.actors.MovementInteraction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.interfaces.IHasGraphics;
import com.neweyjrpg.interfaces.IManagesGraphics;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;
import com.neweyjrpg.util.ClosestPosition;
import com.neweyjrpg.util.Conversion;

public class ActorManager extends Manager implements IManagesGraphics {

	private float boundX, boundY;
	private Color color;
	private IHandlesInteraction handler;
	private boolean isBlocked;
	
	private PlayerActor player;
	public PlayerActor getPlayer() { return this.player; }
	public void setPlayer(PlayerActor player) { this.player = player; }
	public Vector2 getPlayerPos() { return new Vector2(player.getX(), player.getY()); }
	
	private Array<GameActor> actors; //All actors in this scene
	public Array<GameActor> getActors() { return actors; }
	public void setActors(Array<GameActor> actors) { this.actors = actors; }
	
	public GameActor getActorByName(String s) {
		for (GameActor a : actors) {
			if (a.getName().equals(s)) {
				return a;
			}
		}
		return null;
	}
	
	public void addActor(GameActor actor) {
		if (actor.getName() == null || actor.getName().equals("")) {
			actor.setName("Actor");
		}
		String name = actor.getName();
		for (int i=0; !actorNameUnique(actor.getName()); i++) {
			actor.setName(name+i);
		}
		this.actors.add(actor);
	}
	
	private boolean actorNameUnique(String name) {
		for (GameActor actor : this.actors) {
			if (actor.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	private void cleanupActors() {
		for (int i=0; i<actors.size; i++) {
			if (actors.get(i).isDisposed()) {
				this.actors.removeIndex(i);
			}
		}
	}
	
	@Override
	public void massColorLerp(float r, float g, float b, float a, float factor) {
		this.color.lerp(r,g,b,a, factor);
	}
	
	public ActorManager(PlayerActor player, float boundX, float boundY, IProducesInputs controller, IHandlesInteraction handler) {
		this.handler = handler;
		this.player = player;
		this.boundX = boundX;
		this.boundY = boundY;
		this.color = Color.WHITE;
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
			return playerAttack();
		default:
			return false;
		}
	}
	
	private boolean playerAttack() {
		if (!player.isAttacking()) {
			player.setAttacking(true);
			handler.handle(new AttackInteraction(null, "PLAYER"));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean handleDirectionPress(int button) {
		return false;
	}

	@Override
	public void draw(float deltaTime, int yaxis, float offsetX, float offsetY, Batch batch, Enums.Priority priority) {
		//Draw all actors in the scene at their given position, taking into account the camera scrolling
		cleanupActors();
		actors.sort();
		for (GameActor actor : actors) {
			if (actor.getPriority() != priority) { 
				continue; 
			}
			if (actor instanceof IHasGraphics) {
				if (actor.getPhysicsBody().getY() >= yaxis * Constants.TILE_HEIGHT && 
					actor.getPhysicsBody().getY() < (yaxis * Constants.TILE_HEIGHT) + Constants.TILE_HEIGHT ) {
					Vector2 actorSize = ((IHasGraphics)actor).getGraphicSize();
					if (actor.getX() + offsetX + actorSize.x < 0 || actor.getX() + offsetX > Constants.GAME_WIDTH + actorSize.x
					 || actor.getY() + offsetY + actorSize.y < 0 || actor.getY() + offsetY > Constants.GAME_HEIGHT + actorSize.y)
						continue;
					batch.setColor(actor.getColor().cpy().mul(this.color));
					actor.draw(batch, deltaTime, offsetX, offsetY);
					batch.setColor(Color.WHITE);
				}
			}
			
		}
	}
	
	public void block() {
		this.isBlocked = true;
		for (GameActor a : this.actors) {
			a.block();
		}
	}
	public void unblock() {
		this.isBlocked = false;
		for (GameActor a : this.actors) {
			a.unblock();
		}
	}

	@Override
	public boolean act(float deltaTime) {		
		if (player == null || actors == null || actors.size == 0 || this.isBlocked) 
			return false;		
		
		// Non-player actions 
		for (GameActor actor : actors) {
			//if (actor == this.player) continue;
			while (!actor.getInteractionQueue().isEmpty()) {
				Interaction i = actor.getInteractionQueue().removeFirst();
				i.setScene(this.handler);
				handler.handle(i);
			}
						
			//If actor has a controller attached, allow the controller to work.
			if (actor instanceof CharacterActor && actor.getController() != null)
				actor.move(Conversion.dpadToVec(actor.getController().getDirectionalState().getInputs()));
			actor.act(deltaTime);
			
			if (actor.getPhysicsBody().getType() != PhysicalState.StaticBlock) {
				this.detectCollision(actor);
			}
		}
		
		return true;
	}

	/**
	 * Checks if a given actor is colliding with any other actors in the scene.
	 * Also prevents actors from going out of bounds.
	 * @param actor - the actor that is colliding into another actor.
	 * 			NOTE: Static Actors should not be specified as the parameter for this method.
	 */
	private void detectCollision(GameActor actor) {
		actor.keepInBounds(boundX, boundY);
		
		Array<GameActor> sortedActors = new Array<GameActor>(actors);
		sortedActors.sort(new ClosestPosition(actor));
		
		for (int j = 0; j < sortedActors.size; j++) {
			GameActor subject = sortedActors.get(j);			
			if (subject.equals(actor)) {
				continue;
			}
			if (actor.collideInto(subject)) {
				if (player == actor) {
					for (Interaction action : subject.onTouch()) {
						handler.handle(action);
					}
				} else if (player == subject) {
					for (Interaction action : actor.onTouch()) {
						handler.handle(action);
					}
				}
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
			if (sortedActors.get(i) != null && sortedActors.get(i).onAction() != null) {
				if (sortedActors.get(i).getDistance(player) < Constants.CHARA_PHYS_SIZE + 4f) {
					for (Interaction action : sortedActors.get(i).onAction()) {
						handler.handle(action);
					}
				}
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean handle(Interaction interaction) {
		if (interaction instanceof MovementInteraction) {
			MovementInteraction action = (MovementInteraction)interaction;
			Vector2 data = action.process(this).getData();
			if (data != null) {
				action.getTarget().move(data);
			}
		}
		if (interaction instanceof AttackInteraction) { ((AttackInteraction)interaction).process(this); interaction.complete(); }
		if (interaction instanceof DamageInteraction) { ((DamageInteraction)interaction).process(this); interaction.complete(); }
		if (interaction instanceof DisposeInteraction) { ((DisposeInteraction)interaction).process(this); interaction.complete(); }
		if (interaction instanceof CreateActorInteraction) { ((CreateActorInteraction)interaction).process(this); interaction.complete(); }
		return false;
	}
	
	@Override
	public boolean handleButtonState(ButtonInput button) {
		return false;
	}
	
	@Override
	public boolean handleDirectionState(DirectionalInput dir) {
		if (!player.hasActions() && !player.isAttacking()) {
			Vector2 vec = Conversion.dpadToVec(dir.getInputs());
			this.player.move(vec);
			return true;
		}
		return false;
	}
	
	@Override
	public void dispose() {
		for (GameActor actor : actors) {
			actor.dispose();
		}
	}
	
	@Override
	public void onInteractionComplete(Interaction i) {
		// TODO Auto-generated method stub
		
	}
}
