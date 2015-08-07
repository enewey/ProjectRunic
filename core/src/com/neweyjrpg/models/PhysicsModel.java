package com.neweyjrpg.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums.PhysicalState;

public class PhysicsModel {

	private PhysicalState type;
	public PhysicalState getType() {
		return type;
	}

	private Rectangle bounds;
	
	public Rectangle getBounds() {
		return bounds;
	}

	public PhysicsModel(PhysicalState type, Rectangle bounds) {
		this.type = type;
		this.bounds = bounds;
	}
	
	public Vector2 moveOff(PhysicsModel other) {
		if (this.getBounds().overlaps(other.getBounds())) {
			float pWidth = Math.max(this.getBounds().width, other.getBounds().width);
			float pHeight = Math.max(this.getBounds().height, other.getBounds().height);
			
			float moveX = this.getBounds().x - other.getBounds().x;
			int signX = (int)(moveX / Math.abs(moveX));
			float moveY = this.getBounds().y - other.getBounds().y;
			int signY = (int)(moveY / Math.abs(moveY));

			if (moveX < moveY)
				return new Vector2(pWidth * signX, 0); //eject horizontally for base case
			else
				return new Vector2(0, pHeight * signY);
		}
		
		return null;
	}
}
