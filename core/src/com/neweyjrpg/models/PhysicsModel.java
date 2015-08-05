package com.neweyjrpg.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsModel {

	private BodyType type;
	public BodyType getType() {
		return type;
	}

	private Rectangle bounds;
	
	public Rectangle getBounds() {
		return bounds;
	}

	public PhysicsModel(BodyType type, Rectangle bounds) {
		this.type = type;
		this.bounds = bounds;
	}
	
	public Vector2 moveOff(PhysicsModel other) {
		if (this.getBounds().overlaps(other.getBounds())) {
			float pWidth = Math.max(this.getBounds().width, other.getBounds().width);
			float pHeight = Math.max(this.getBounds().height, other.getBounds().height);
			
			float moveX = this.getBounds().x - other.getBounds().x;
			float moveY = this.getBounds().y - other.getBounds().y;
			if (moveX == 0 && moveY == 0)
				return new Vector2(pWidth, 0);
			
			if (Math.abs(moveX) > pWidth) moveX = 0;
			if (Math.abs(moveY) > pHeight) moveY = 0;
			
			if (Math.abs(moveX) > Math.abs(moveY) && moveY != 0)
				moveX = 0;
			else if (moveX != 0)
				moveY = 0;
			
			return new Vector2(moveX, moveY);
		}
		
		return null;
	}
}
