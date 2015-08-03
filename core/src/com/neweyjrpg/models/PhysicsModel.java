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
			float moveX = this.getBounds().x - other.getBounds().x;
			float moveY = this.getBounds().y - other.getBounds().y;
			if (Math.abs(moveX) < Math.abs(moveY))
				moveX = 0;
			else
				moveY = 0;
			
			return new Vector2(moveX, moveY);
		}
		
		return null;
	}
}
