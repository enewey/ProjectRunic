package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IHandlesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class GameActor implements IHandlesInputs {
	
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
		this.dir = Dir.DOWN;
		this.isMoving = false;
		movespeed = 2f;
	}
	
	//Methods
	public void draw(SpriteBatch batch, float deltaTime) {
		this.animation.draw(batch, deltaTime, this.dir, this.isMoving);
	}
	
	public void setPosition(float x, float y) {
		animation.setPosition(x, y);
	}
	public float[] getPosition() {
		return animation.getPosition();
	}
	
	public void move(float x, float y){
		if (x == 0 && y == 0) isMoving = false;
		else isMoving = true;
		
		if (x<0) this.dir=Dir.LEFT;
		else if (x>0) this.dir=Dir.RIGHT;
		if (y<0) this.dir=Dir.DOWN;
		else if (y>0) this.dir=Dir.UP;
		this.animation.translate(x, y);
	}
	
	@Override
	public void moveFromInput(DirectionalInput dirs) {
		float tx=0f, ty=0f;
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
