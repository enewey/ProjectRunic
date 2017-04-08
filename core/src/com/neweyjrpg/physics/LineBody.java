package com.neweyjrpg.physics;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.util.Line;

public class LineBody extends PhysicsBody {

	private Line line;
	public Line getBounds() {
		return this.line;
	}
	
	public float getX() {
		return this.line.a.x;
	}
	public float getY() {
		return this.line.a.y;
	}
	public float getWidth() {
		return Math.abs(this.line.a.x - this.line.b.x);
	}
	public float getHeight() {
		return Math.abs(this.line.a.y - this.line.b.y);
	}
	
	/**
	 * Moves the head of the line to the point x,y, then moves the tail of the line
	 * the exact same distance.
	 */
	public void setPosition(float x, float y) {
		float diffx = this.line.a.x - x;
		float diffy = this.line.a.y - y;
		this.line.a.set(x,y);
		this.line.b.set(this.line.b.x - diffx, this.line.b.y - diffy);
	}
	
	public void setTailPosition(float x, float y) {
		float diffx = this.line.b.x - x;
		float diffy = this.line.b.y - y;
		this.line.b.set(x,y);
		this.line.a.set(this.line.a.x - diffx, this.line.a.y - diffy);
	}
	
	@Override
	public boolean overlaps(PhysicsBody other) {
		if (other instanceof BlockBody) {
			return ((BlockBody)other).lineDoesIntersect(this.line.a, this.line.b);
		} else if (other instanceof LineBody) {
			return ((LineBody)other).getBounds().overlaps(this.line);
		} else {
			return false;
		}
	}

	@Override
	public Vector2 getCenter() {
		return new Vector2((this.line.b.x - this.line.a.x)/2, (this.line.b.y - this.line.a.y)/2);
	}

	@Override
	public void keepInBounds(float boundx, float boundy) {
		
	}
}
