package com.neweyjrpg.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interaction.MovementInteraction;
import com.neweyjrpg.interfaces.ICanCollide;
import com.neweyjrpg.interfaces.IHandlesCollision;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.interfaces.IProducesInteraction;
import com.neweyjrpg.models.ActorState;
import com.neweyjrpg.physics.BlockBody;
import com.neweyjrpg.physics.PhysicsBody;
import com.neweyjrpg.util.Conversion;

public abstract class GameActor extends Actor implements Comparable<GameActor>, ICanCollide<GameActor>, IProducesInteraction {
		
	//Fields
	protected PhysicsBody phys;
	public PhysicsBody getPhysicsBody() {	return this.phys; }
	protected float oldX, oldY;
	
	private boolean disposed;
	public boolean isDisposed() {
		return this.disposed;
	}
	
	private HashMap<String, ActorState> stateMap;
	public HashMap<String,ActorState> getState() { return this.stateMap; }
	
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
		this.stateMap = new HashMap<String, ActorState>();
	}
	
	//Methods
	public abstract void draw(Batch batch, float deltaTime, float offsetX, float offsetY);
	
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
		this.addMove(Actions.moveBy(x,y, this.actionSpeed));
	}
	/**
	 * move the actor (+x,+y) relative to its location, scaling the actionSpeed by s.
	 */
	public void moveDistance(float x, float y, float s) {
		this.addMove(Actions.moveBy(x*s, y*s, this.actionSpeed*s));
	}
	public void moveDistance(float x, float y, float s, final MovementInteraction mi) {
		this.addMove(Actions.moveBy(x*s,  y*s, this.actionSpeed*s));
		this.addCompletedAction(mi);
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
		this.queueAction(move);
	}
	/**
	 * Adds a MoveByAction directly to the actor's Actions.
	 * Using this method will cause the actor to move instantly, regardless of whether they are moving or not.
	 * Useful for events that are meant to override control (e.g. damage blowback, pushing objects, etc.)
	 */
	public void forceMove(MoveByAction move) {
		this.addAction(move);
	}
	
	public void wait(float sec) {
		this.queueAction(Actions.moveBy(0f, 0f, sec));
	}
	
	public void wait(float sec, MovementInteraction i) {
		this.wait(sec);
		this.addCompletedAction(i);
	}
	
	private void addCompletedAction(final MovementInteraction i) {
		this.queueAction(new Action() {
			@Override
			public boolean act(float delta) {
				i.complete();
				return true;
			}
		});
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
		
		this.oldX = this.phys.getX();
		this.oldY = this.phys.getY();
		
		super.act(deltaTime);
		alignPhysicsModelToActor();
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
		alignPhysicsModelToActor();
	}
	
	protected void alignPhysicsModelToActor() {
		this.getPhysicsBody().setPosition(getX() + physPaddingX, getY() + physPaddingY);
	}
	
	protected void alignActorToPhysicsModel() {
		this.setX(getPhysicsBody().getX() - physPaddingX);
		this.setY(getPhysicsBody().getY() - physPaddingY);
	}
	
	public void setPhysicalPosition(float x, float y) {
		this.getPhysicsBody().setPosition(x, y);
		alignActorToPhysicsModel();
	}
	
	public void movePhysicalPosition(float x, float y) {
		this.getPhysicsBody().setPosition(
				this.getPhysicsBody().getX() + x, 
				this.getPhysicsBody().getY() + y);
		this.oldX = this.phys.getX();
		this.oldY = this.phys.getY();
		alignActorToPhysicsModel();
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.getX(), this.getY());
	}
	public Vector2 getOldPosition() {
		return new Vector2(this.oldX, this.oldY);
	}
	
	public float getDistance(GameActor subject) {
		Vector2 a = this.getPhysicsBody().getCenter();
		Vector2 b = subject.getPhysicsBody().getCenter();
		
		return (float)Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}
	
	public void keepInBounds(float boundx, float boundy) {
		this.phys.keepInBounds(boundx,boundy);
		this.alignActorToPhysicsModel();
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
		return this.getCollider().checkCollision(this, obj);
		
	}
	@Override
	public boolean collisionFrom(GameActor obj) {
		return obj.getCollider().checkCollision(obj, this);	
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
	
	public void dispose() {
		this.disposed = true;
	}
	public abstract IProducesInputs getController();
}
