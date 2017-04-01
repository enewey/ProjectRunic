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
}
