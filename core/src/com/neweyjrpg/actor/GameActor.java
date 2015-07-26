package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IHandlesInputs;

public class GameActor implements IHandlesInputs {
	
	private final static float MOVEMENT_DISTANCE = 2f;
	
	//Fields
	private ActorAnimation animation;
	public ActorAnimation getAnimation() { return animation; }
	public void setAnimation(ActorAnimation animation) {	this.animation = animation;	}
	
	private Dir dir;
	public Dir getDir() {	return dir;	}
	public void setDir(Dir dir) {	this.dir = dir;	}
	
	private boolean isMoving;

	//Constructors
	public GameActor(Texture charaSet, int pos, float x, float y){
		this.animation = new ActorAnimation(charaSet, pos);
		this.setPosition(x, y);
		this.dir = Dir.DOWN;
		this.isMoving = false;
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
	public void moveFromInput(boolean[] dirs) {
		float tx=0f, ty=0f;
		if (dirs[0] && !dirs[2])
			ty = MOVEMENT_DISTANCE;
		else if (!dirs[0] && dirs[2])
			ty = -MOVEMENT_DISTANCE;
		if (dirs[1] && !dirs[3])
			tx = MOVEMENT_DISTANCE;
		else if (!dirs[1] && dirs[3])
			tx = -MOVEMENT_DISTANCE;
		this.move(tx, ty);
	}
}
