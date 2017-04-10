package com.neweyjrpg.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums.PhysicalState;

public class BlockBody extends PhysicsBody {

	private PhysicalState type;
	public PhysicalState getType() {
		return type;
	}

	private Rectangle bounds;
	public Rectangle getBounds() {
		return bounds;
	}
	public float getX() {
		return this.bounds.x;
	}
	public float getY() {
		return this.bounds.y;
	}
	public float getWidth() {
		return this.bounds.width;
	}
	public float getHeight() {
		return this.bounds.height;
	}
	public void setPosition(float x, float y) {
		this.bounds.setPosition(x,y);
	}
	
	public Vector2 getCenter() {
		Vector2 ret = new Vector2();
		this.bounds.getCenter(ret);
		return ret;
	}
	
	@Override
	public boolean overlaps(PhysicsBody other) {
		if (other instanceof BlockBody) {
			BlockBody block = (BlockBody)other;
			return this.bounds.overlaps(block.getBounds());
		}
		else if (other instanceof LineBody) {
			LineBody line = (LineBody)other;
			return this.lineDoesIntersect(line.getLine().a, line.getLine().b);
		}
		else {
			return false;
		}
	}
	
	public boolean lineDoesIntersect(Vector2 a, Vector2 b) {
		Polygon poly = new Polygon(new float[] {
				this.bounds.x, this.bounds.y,
				this.bounds.x + this.bounds.width, this.bounds.y,
				this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.height,
				this.bounds.x, this.bounds.y + this.bounds.height
				});
		return Intersector.intersectSegmentPolygon(a, b, poly);
	}

	public BlockBody(PhysicalState type, Rectangle bounds) {
		this.type = type;
		this.bounds = bounds;
	}
	
	@Override
	public void keepInBounds(float boundx, float boundy) {
		if (this.bounds.x < 0)
			this.setPosition(0, this.bounds.y);
		else if (this.bounds.x > boundx - this.bounds.width)
			this.setPosition(boundx - this.bounds.width, this.bounds.y);
		
		if (this.bounds.y < 0)
			this.setPosition(this.bounds.x, 0);
		else if (this.bounds.y > boundy - this.bounds.height)
			this.setPosition(this.bounds.x, boundy - this.bounds.height);
		
	}
}
