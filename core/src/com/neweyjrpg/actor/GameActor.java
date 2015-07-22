package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameActor {

	//Constructors
	
	public GameActor(Sprite sprite){
		this.baseSprite = sprite;
	}
	public GameActor(Sprite base, Sprite layer){
		this.baseSprite = base;
		this.layerSprite = layer;
	}
	public GameActor(Sprite sprite, float x, float y){
		this.baseSprite = sprite;
		this.baseSprite.setPosition(x, y);
	}
	public GameActor(Sprite base, Sprite layer, float x, float y){
		this.baseSprite = base;
		this.layerSprite = layer;
		this.baseSprite.setPosition(x, y);
		this.layerSprite.setPosition(x, y);
	}
	
	//Members
	
	private Sprite baseSprite;
	public Sprite getSprite() {	return baseSprite; }
	public void setSprite(Sprite sprite) {	this.baseSprite = sprite; }
	
	private Sprite layerSprite;
	public Sprite getLayerSprite() { return layerSprite; }
	public void setLayerSprite(Sprite layerSprite) { 
		this.layerSprite = layerSprite;
		this.layerSprite.setPosition(this.baseSprite.getX(), this.baseSprite.getY());
	}
	
	//Methods
	
	public void draw(SpriteBatch batch) {
		this.baseSprite.draw(batch);
		this.layerSprite.draw(batch);
	}
	
	public void move(float x, float y){
		this.baseSprite.translateX(x);
		this.baseSprite.translateY(y);
		this.layerSprite.translateX(x);
		this.layerSprite.translateY(y);
	}
	
	public void setPosition(float x, float y) {
		this.baseSprite.setPosition(x, y);
		this.layerSprite.setPosition(x, y);
	}
	public float[] getPosition() {
		return new float[]{ this.baseSprite.getX(),	this.baseSprite.getY() };
	}
	
}
