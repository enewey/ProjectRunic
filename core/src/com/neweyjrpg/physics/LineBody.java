package com.neweyjrpg.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.util.Line;

public class LineBody extends PhysicsBody {

	private Line line;
	public Line getLine() {
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
	
	public LineBody(PhysicalState type, float ax, float ay, float bx, float by) {
		this.type = type;
		this.line = new Line(new Vector2(ax,ay), new Vector2(bx,by));
	}
	
	public LineBody(PhysicalState type, Vector2 a, Vector2 b) {
		this.type = type;
		this.line = new Line(a,b);
	}
	
	public LineBody(PhysicalState type, Line line) {
		this.type = type;
		this.line = line;
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
	
	public void setLine(Line line) {
		this.line = line;
	}
	
	@Override
	public boolean overlaps(PhysicsBody other) {
		if (other instanceof BlockBody) {
			return ((BlockBody)other).lineDoesIntersect(this.line.a, this.line.b);
		} else if (other instanceof LineBody) {
			return ((LineBody)other).getLine().overlaps(this.line);
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
	
	public TextureRegion getDebugTexture() {
		Vector2 botLeft = this.line.getBottomLeft();
		int x1 = (int)(this.line.a.cpy().sub(botLeft).x);
		int y1 = (int)(this.line.a.cpy().sub(botLeft).y);
		int x2 = (int)(this.line.b.cpy().sub(botLeft).x);
		int y2 = (int)(this.line.b.cpy().sub(botLeft).y);
		
		Pixmap pxm = new Pixmap((int)this.getWidth()+2, (int)this.getHeight()+2, Format.RGBA8888);
		pxm.setColor(Color.BLUE);
		pxm.drawLine(x1, (pxm.getHeight()-1) - y1, x2, (pxm.getHeight()-1) - y2);
		pxm.setColor(Color.RED);
		pxm.drawPixel(x1, pxm.getHeight() - y1);
		pxm.setColor(Color.GREEN);
		pxm.drawPixel(x2, pxm.getHeight() - y2);
		
		TextureRegion ret = new TextureRegion(new Texture(pxm));
		pxm.dispose();
		return ret;
	}
}
