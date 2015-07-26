package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neweyjrpg.enums.Enums.Direction;
import com.neweyjrpg.graphic.ActorAnimation;

public class GameActor {

	//Constructors
	public GameActor(Texture charaSet, int pos, float x, float y){
		this.animation = new ActorAnimation(charaSet, pos);
		this.setPosition(x, y);
		this.dir = Direction.DOWN;
		this.isMoving = false;
	}
	
//	public GameActor(Sprite sprite){
//		this.baseSprite = sprite;
//	}
//	public GameActor(Sprite base, Sprite layer){
//		this.baseSprite = base;
//		this.layerSprite = layer;
//	}
//	public GameActor(Sprite sprite, float x, float y){
//		this.baseSprite = sprite;
//		this.baseSprite.setPosition(x, y);
//	}
//	public GameActor(Sprite base, Sprite layer, float x, float y){
//		this.baseSprite = base;
//		this.layerSprite = layer;
//		this.baseSprite.setPosition(x, y);
//		this.layerSprite.setPosition(x, y);
//	}
	
	
	//Members
	
//	private Sprite baseSprite;
//	public Sprite getSprite() {	return baseSprite; }
//	public void setSprite(Sprite sprite) {	this.baseSprite = sprite; }
//	
//	private Sprite layerSprite;
//	public Sprite getLayerSprite() { return layerSprite; }
//	public void setLayerSprite(Sprite layerSprite) { 
//		this.layerSprite = layerSprite;
//		this.layerSprite.setPosition(this.baseSprite.getX(), this.baseSprite.getY());
//	}
	
	private ActorAnimation animation;
	public ActorAnimation getAnimation() { return animation; }
	public void setAnimation(ActorAnimation animation) {	this.animation = animation;	}
	
	private Direction dir;
	public Direction getDir() {	return dir;	}
	public void setDir(Direction dir) {	this.dir = dir;	}
	
	private boolean isMoving;
	
	//Methods
	public void draw(SpriteBatch batch, float deltaTime) {
		this.animation.draw(batch, deltaTime, this.dir, this.isMoving);
	}
	
	public void move(float x, float y){
		if (x == 0 && y == 0) isMoving = false;
		else isMoving = true;
		
		if (x<0) this.dir=Direction.LEFT;
		else if (x>0) this.dir=Direction.RIGHT;
		if (y<0) this.dir=Direction.DOWN;
		else if (y>0) this.dir=Direction.UP;
		this.animation.translate(x, y);
	}
	
	public void setPosition(float x, float y) {
		animation.setPosition(x, y);
	}
	public float[] getPosition() {
		return animation.getPosition();
	}
}
