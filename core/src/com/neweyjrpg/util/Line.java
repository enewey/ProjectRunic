package com.neweyjrpg.util;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class Line {

	public Vector2 a;
	public Vector2 b;
	
	public Line() {
	}
	
	public Line(Vector2 a, Vector2 b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean overlaps(Line other) {
		return Intersector.intersectLines(a, b, other.a, other.b, null);
	}	
}
