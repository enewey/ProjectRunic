package com.neweyjrpg.physics;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums.PhysicalState;

public abstract class PhysicsBody {
	
	public abstract boolean overlaps(PhysicsBody other);
	public abstract Vector2 getCenter();
	
	protected PhysicalState type;
	public PhysicalState getType() {
		return type;
	}
	
	public abstract float getX();
	public abstract float getY();
	public abstract float getWidth();
	public abstract float getHeight();
	public abstract void setPosition(float x, float y);
	public abstract void keepInBounds(float boundx, float boundy);
	
	public Vector2 moveOff(PhysicsBody other) {
		if (this.overlaps(other)) {
			
			float xdiff = this.getX() - other.getX();
			float ydiff = this.getY() - other.getY();
			
			if (Math.abs(xdiff) < Math.abs(ydiff)) {
				float twidth  = this.getWidth();
				float owidth  = other.getWidth();
				if (xdiff < other.getWidth() / 2.0) {
					//eject left
					return new Vector2(-(twidth + xdiff), 0);
				} else {
					//eject right
					return new Vector2((owidth - xdiff), 0);
				}
				
			} else {
				float theight = this.getHeight();
				float oheight = other.getHeight();
				if (ydiff < other.getHeight() / 2.0) {
					//eject down
					return new Vector2(-(theight + ydiff), 0);
				} else {
					//eject up
					return new Vector2((oheight - ydiff), 0);
				}
			}
		}
		
		return null;
	}
}
