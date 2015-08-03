package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IHandlesInputs;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;
import com.neweyjrpg.models.PhysicsModel;

public class GameActor extends Actor implements IHandlesInputs {
	
	//Fields
	private ActorAnimation animation;
	public ActorAnimation getAnimation() { return animation; }
	public void setAnimation(ActorAnimation animation) {	this.animation = animation;	}
	
	private IProducesInputs controller;
	
	private Dir dir;
	public Dir getDir() {	return dir;	}
	public void setDir(Dir dir) {	this.dir = dir;	}
	
	protected float movespeed;
	public float getMovespeed() { return movespeed;	}
	public void setMovespeed(float movespeed) {	this.movespeed = movespeed;	}
	
	protected boolean isMoving;
	protected float actionSpeed;
	
	private float oldX, oldY;
	private PhysicsModel phys;
	public PhysicsModel getPhysicsModel() {	return this.phys; }
	
	//Constructors
	public GameActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys){
		this.animation = new ActorAnimation(charaSet, pos);
		
		this.oldX = x; //Values for resetting movement on collisions
		this.oldY = y; 
		this.phys = phys;
		
		this.setPosition(x, y);
		this.dir = Dir.DOWN;
		this.isMoving = false;
		this.movespeed = 2f;
		this.actionSpeed = 0.01f;
	}
	
	//Methods
	@Override
	public void draw(Batch batch, float deltaTime) {
		if (!isMoving)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION; 
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), this.getX(), this.getY());
	}
	
	public void draw(Batch batch, float deltaTime, float x, float y) {
		if (!isMoving)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION; 
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), x, y);
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
		this.getPhysicsModel().getBounds().setPosition(x, y);
	}
	
	@Override
	public void act(float delta) {
		if (controller != null && !controller.getDirectionalInput().isEmpty())
			this.moveFromInput(controller.getDirectionalInput());
		else
			this.isMoving = false;
		
		this.oldX = this.getX();
		this.oldY = this.getY();
		
		super.act(delta);
		this.getPhysicsModel().getBounds().setPosition(getX(), getY());
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.getX(), this.getY());
	}
	public Vector2 getOldPosition() {
		return new Vector2(this.oldX, this.oldY);
	}
	
	public void move(float x, float y) {
		this.isMoving = true;
		
		if (x<0) 
			this.dir=Dir.LEFT;
		else if (x>0) 
			this.dir=Dir.RIGHT;
		
		if (y<0) 
			this.dir=Dir.DOWN;
		else if (y>0) 
			this.dir=Dir.UP; 
		
		this.addAction(Actions.moveBy(x, y, this.actionSpeed));
		//this.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void moveFromInput(DirectionalInput input) {
		
		boolean[] dirs = input.getInputs();
		float tx=0f, ty=0f;
		if (dirs[0])
			ty += movespeed;
		if (dirs[1])
			tx += movespeed;
		if (dirs[2])
			ty -= movespeed;
		if (dirs[3])
			tx -= movespeed;
		
		if (Math.abs(tx) > 0 && Math.abs(ty) > 0) {
			tx *= 0.7071f; //Roughly sqrt(2)/2, 45deg on unit circle
			ty *= 0.7071f;
		}
		
		move(tx, ty);
	}
	
	@Override
	public void setController(IProducesInputs controller) {
		this.controller = controller;
	}

}
