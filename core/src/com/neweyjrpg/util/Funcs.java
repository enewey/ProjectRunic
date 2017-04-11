package com.neweyjrpg.util;

import com.badlogic.gdx.math.Vector2;

public class Funcs {

	public static Vector2 roundPixels(float x, float y) {
		int xfloor = (int)Math.floor(x * 10); //truncate to one decimal
		xfloor = xfloor - (xfloor % 2); //round decimal to nearest even number
		int yfloor = (int)Math.floor(y * 10);
		yfloor = yfloor - (yfloor % 2);
		
		return new Vector2((float)xfloor / 10.0f, (float)yfloor / 10.0f);
	}
	
	public static float slope(Vector2 a, Vector2 b) {
		return (b.y - a.y) / (b.x - a.x);
	}
	
	public static Vector2 vectorWithSlope(float length, float slope) {
		if (Float.isInfinite(slope)) {
			return new Vector2(0, length);
		}
		float run = (float)(length / Math.sqrt((slope*slope) + 1));
		float rise = slope * run;
		return new Vector2(run, rise);
	}
	
	public static Vector2 getMoveAwayFrom(Vector2 base, Vector2 other) {
		Vector2 v = vectorWithSlope(1, Funcs.slope(base, other));
		if ((other.x > base.x && v.x > 0) || (other.x < base.x && v.x < 0)) v.x *= -1;
		if ((other.y > base.y && v.y > 0) || (other.y < base.y && v.y < 0)) v.y *= -1;
		return v;
	}
	
	public static Vector2 getMoveTowards(Vector2 base, Vector2 other) {
		Vector2 v = vectorWithSlope(1, Funcs.slope(base, other));
		if ((other.x > base.x && v.x < 0) || (other.x < base.x && v.x > 0)) v.x *= -1;
		if ((other.y > base.y && v.y < 0) || (other.y < base.y && v.y > 0)) v.y *= -1;
		return v;
	}
}
