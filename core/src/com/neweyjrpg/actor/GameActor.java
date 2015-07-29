package com.neweyjrpg.actor;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IHandlesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class GameActor extends Actor implements IHandlesInputs {
	
	protected final static float ACTION_SPEED = 0.01f;
	
	//Fields
	private ActorAnimation animation;
	public ActorAnimation getAnimation() { return animation; }
	public void setAnimation(ActorAnimation animation) {	this.animation = animation;	}
	
	private Dir dir;
	public Dir getDir() {	return dir;	}
	public void setDir(Dir dir) {	this.dir = dir;	}
	
	private boolean isMoving;
	protected float movespeed;
	public float getMovespeed() { return movespeed;	}
	public void setMovespeed(float movespeed) {	this.movespeed = movespeed;	}
	
	//Constructors
	public GameActor(Texture charaSet, int pos, float x, float y){
		this.animation = new ActorAnimation(charaSet, pos);
		this.setPosition(x, y);
		this.setBounds(x, y, 
				this.animation.getAnim(Dir.UP).getKeyFrame(0).getRegionWidth(), 
				this.animation.getAnim(Dir.UP).getKeyFrame(0).getRegionWidth());
		this.dir = Dir.DOWN;
		this.isMoving = false;
		movespeed = 2f;
	}
	
	//Methods
	@Override
	public void draw(Batch batch, float deltaTime) {
		if (!this.isMoving)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION; 
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), getX(), getY());
		
	}
	
	
	@Override
	public void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	public float[] getPosition() {
		return new float[]{getX(), getY()};
	}
	
	public void move(float x, float y){
		if (x == 0 && y == 0) 	
			isMoving = false;
		else 					
			isMoving = true;
		
		if (x<0) 
			this.dir=Dir.LEFT;
		else if (x>0) 
			this.dir=Dir.RIGHT;
		
		if (y<0) 
			this.dir=Dir.DOWN;
		else if (y>0) 
			this.dir=Dir.UP;
		
		MoveToAction action = new MoveToAction();
		action.setPosition(getX() + x, getY() + y);
		action.setDuration(ACTION_SPEED);
		this.addAction(action);
		this.act(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void moveFromInput(DirectionalInput input) {
		float tx=0f, ty=0f;
		Stack<Dir> dirs = input.getInputs();
		while (!dirs.isEmpty()) {
			Dir d = dirs.pop();
			switch (d){
			case UP:
				ty += movespeed;
				break;
			case RIGHT:
				tx += movespeed;
				break;
			case DOWN:
				ty -= movespeed;
				break;
			case LEFT:
				tx -= movespeed;
				break;
			}
		}
		move(tx, ty);
	}
}
