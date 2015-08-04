package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.models.PhysicsModel;

public abstract class GameActor extends Actor implements Comparable<GameActor> {
	
	//Fields
	protected PhysicsModel phys;
	public PhysicsModel getPhysicsModel() {	return this.phys; }
	protected float oldX, oldY;
	
	//Constructor
	public GameActor(float x, float y, PhysicsModel phys){
		this.phys = phys;
		this.setPosition(x, y);
		this.oldX = phys.getBounds().x; //Values for resetting movement on collisions
		this.oldY = phys.getBounds().y; 
	}
	
	//Methods
	public abstract void draw(Batch batch, float deltaTime, float x, float y);
	public abstract void move(float x, float y);
	
	@Override
	public void act(float deltaTime) {
		this.oldX = this.phys.getBounds().x;
		this.oldY = this.phys.getBounds().y;
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
		this.getPhysicsModel().getBounds().setPosition(getX() + (Constants.CHARA_WIDTH / 4), getY());
	}
	
	public void setPhysicalPosition(float x, float y) {
		this.getPhysicsModel().getBounds().setPosition(x, y);
		alignActorToPhysicsModel();
	}
	
	public void movePhysicalPosition(float x, float y) {
		this.getPhysicsModel().getBounds().setPosition(this.getPhysicsModel().getBounds().x + x, 
				this.getPhysicsModel().getBounds().y + y);
		alignActorToPhysicsModel();
	}
	
	private void alignActorToPhysicsModel() {
		this.setX(getPhysicsModel().getBounds().x - (Constants.CHARA_WIDTH/4));
		this.setY(getPhysicsModel().getBounds().y);
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.getX(), this.getY());
	}
	public Vector2 getOldPosition() {
		return new Vector2(this.oldX, this.oldY);
	}
	
	
	@Override
	public int compareTo(GameActor o) {
		return (int)((o.getY()*1000) - (this.getY()*1000));
	}

}
