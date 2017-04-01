package com.neweyjrpg.actor;

import java.util.ArrayList;
import java.util.LinkedList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.interfaces.ICanCollide;
import com.neweyjrpg.interfaces.IHandlesCollision;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.interfaces.IProducesInteraction;
import com.neweyjrpg.interfaces.Interaction;
import com.neweyjrpg.physics.BlockBody;
import com.neweyjrpg.util.Conversion;
import com.neweyjrpg.util.Funcs;

public abstract class GameActor extends Actor implements Comparable<GameActor>, ICanCollide<GameActor>, IProducesInteraction {
	
	//Fields
	protected BlockBody phys;
	public BlockBody getPhysicsModel() {	return this.phys; }
	protected float oldX, oldY;
	
	protected float physPaddingX, physPaddingY; //Where the physics model sits relative to the sprite/animation
	
	//Object that handles collision events
	protected IHandlesCollision<GameActor> collider;
	public IHandlesCollision<GameActor> getCollider() { return this.collider; }
	public void setCollider(IHandlesCollision<GameActor> collider) { this.collider = collider; }
	
	//Interactions are a way for actors to transfer unique instructions to its interaction handler
	protected ArrayList<Interaction> onTouchInteraction;
	public void setOnTouchInteraction(ArrayList<Interaction> i) { this.onTouchInteraction = i; }
	public void addOnTouchInteraction(Interaction i) {
		if (this.onTouchInteraction == null) {
			this.onTouchInteraction = new ArrayList<Interaction>();
		}
		this.onTouchInteraction.add(i); 
	}
	
	protected ArrayList<Interaction> onActionInteraction;
	public void setOnActionInteraction(ArrayList<Interaction> i) { this.onActionInteraction = i; }
	public void addOnActionInteraction(Interaction i) {
		if (this.onActionInteraction == null) {
			this.onActionInteraction = new ArrayList<Interaction>();
		}
		this.onActionInteraction.add(i); 
	}
	
	protected float actionSpeed;
	public float getActionSpeed() {	return actionSpeed;	}
	public void setActionSpeed(float actionSpeed) {	this.actionSpeed = actionSpeed;	}
	
	protected boolean isMoving;
	public boolean isMoving() { return this.isMoving; }
	
	private Enums.Priority priority;
	public Enums.Priority getPriority() { return this.priority; }
	
	//Constructor
	public GameActor(float x, float y, BlockBody phys, Enums.Priority priority) {
		this.phys = phys;
		this.setPosition(x, y);
		this.oldX = phys.getBounds().x; //Values for resetting movement on collisions
		this.oldY = phys.getBounds().y;
		this.priority = priority;
		
		physPaddingX = 0f;
		physPaddingY = 0f;
		
		this.actionQueue = new LinkedList<Action>();
	}
	
	//Methods
	public void draw(Batch batch, float deltaTime) {
		batch.setColor(this.getColor());
	}
	public void draw(Batch batch, float deltaTime, float x, float y) {
		batch.setColor(this.getColor());
	}
	
	/**
	 * Movement
	 * 
	 * GameActors have a queue of move actions. When the actor's act() method is called,
	 * the game will check if the actor is currently moving (i.e. has movement Actions) before
	 * adding moves from the moveQueue. If the actor has no Actions, it will take the oldest Action
	 * from the moveQueue and add it to the actor's Actions.
	 * 
	 *  The moveQueue is also used to determine if an actor is moving, what direction to face, etc.
	 */
	
	protected LinkedList<Action> actionQueue;
	
	/**
	 * move - this will move the actor (+x,+y) relative to its location, in actionSpeed seconds.
	 */
	public void move(float x, float y) { 
		this.addMove(Actions.moveBy(x, y, this.actionSpeed));
	}
	/**
	 * move the actor (+x,+y) relative to its location, scaling the actionSpeed by s.
	 */
	public void moveDistance(float x, float y, float s) {
		this.addMove(Actions.moveBy(x*s, y*s, this.actionSpeed*s));
	}
	/**
	 * move the actor (+x,+y) relative to its location in zero seconds.
	 */
	public void moveInstant(float x, float y) {
		this.addMove(Actions.moveBy(x, y, 0f));
	}
	public void move(Vector2 v) { this.move(v.x, v.y);	}
	public void move(Vector2 v, float s) { this.moveDistance(v.x, v.y, s); }
	public void moveOnce(Dir dir) { this.move(Conversion.dirToVec(dir)); }
	
	/**
	 * Adds a MoveByAction directly to the move queue.
	 */
	public void addMove(MoveByAction move) {
		this.actionQueue.add(move);
	}
	/**
	 * Adds a MoveByAction directly to the actor's Actions.
	 * Using this method will cause the actor to move instantly, regardless of whether they are moving or not.
	 * Useful for events that are meant to override control (e.g. damage blowback, pushing objects, etc.)
	 */
	public void forceMove(MoveByAction move) {
		this.addAction(move);
	}
	
	/**
	 * DO NOT ADD MOVETOACTIONS. WE SAY FUCK MOVETOACTIONS.
	 * @param a
	 */
	public void queueAction(Action a) {
		this.actionQueue.add(a);
	}
	
	public void advanceQueue() {
		this.addAction(actionQueue.remove());
	}

	public MoveByAction getMovementAction() {
		for (Action a : this.getActions()) {
			if (a instanceof MoveByAction) {
				return (MoveByAction)a;
			}
		}
		return null;
	}
	
	public MoveByAction getMoveActionFromQueue() {
		for (Action a : this.actionQueue) {
			if (a instanceof MoveByAction) {
				return (MoveByAction)a;
			}
		}
		return null;
	}
	
	@Override
	public void act(float deltaTime) {
		//Determine and set moving state, and place actions from queue into actor actions.
		this.isMoving = false;
		if (!this.actionQueue.isEmpty()) {
			this.isMoving = true;
			if (!this.hasActions()) {
				this.advanceQueue();
			}
		}
		
		this.oldX = this.phys.getBounds().x;
		this.oldY = this.phys.getBounds().y;
		
		Vector2 roundPos = Funcs.roundPixels(this.getX(), this.getY());
		this.setX(roundPos.x);
		this.setY(roundPos.y);
		
		super.act(deltaTime);
		alignPhysicsModelToActor();
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
		alignPhysicsModelToActor();
	}
	
	private void alignPhysicsModelToActor() {
		this.getPhysicsModel().getBounds().setPosition(getX() + physPaddingX, getY() + physPaddingY);
	}
	
	public void setPhysicalPosition(float x, float y) {
		this.getPhysicsModel().getBounds().setPosition(x, y);
		alignActorToPhysicsModel();
	}
	
	public void movePhysicalPosition(float x, float y) {
		this.getPhysicsModel().getBounds().setPosition(
				this.getPhysicsModel().getBounds().x + x, 
				this.getPhysicsModel().getBounds().y + y);
		this.oldX = this.phys.getBounds().x;
		this.oldY = this.phys.getBounds().y;
		alignActorToPhysicsModel();
	}
	
	private void alignActorToPhysicsModel() {
		this.setX(getPhysicsModel().getBounds().x - physPaddingX);
		this.setY(getPhysicsModel().getBounds().y - physPaddingY);
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.getX(), this.getY());
	}
	public Vector2 getOldPosition() {
		return new Vector2(this.oldX, this.oldY);
	}
	
	public float getDistance(GameActor subject) {
		Vector2 a = this.getPhysicsModel().getCenter();
		Vector2 b = subject.getPhysicsModel().getCenter();
		
		return (float)Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}
	
	@Override
	/**
	 * Comparator sorts by Y position on screen
	 * sorted in order to draw items closer to bottom last
	 */
	public int compareTo(GameActor o) {
		return (int)((o.getY()*1000) - (this.getY()*1000));
	}
	
	@Override
	public boolean collideInto(GameActor obj) {
		return this.getCollider().handleCollision(this, obj);
		
	}
	@Override
	public boolean collisionFrom(GameActor obj) {
		return obj.getCollider().handleCollision(obj, this);	
	}
	
	@Override
	public ArrayList<Interaction> onAction() {
		if (this.onActionInteraction == null) {
			this.onActionInteraction = new ArrayList<Interaction>();
		}
		return this.onActionInteraction;
	}
	@Override
	public ArrayList<Interaction> onTouch() {
		if (this.onTouchInteraction == null) {
			this.onTouchInteraction = new ArrayList<Interaction>();
		}
		return this.onTouchInteraction;
	}
	
	public abstract Vector2 getSpriteSize();
	public abstract void dispose();
	public abstract IProducesInputs getController();
}
