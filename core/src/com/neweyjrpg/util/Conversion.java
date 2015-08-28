package com.neweyjrpg.util;

import com.badlogic.gdx.math.Vector2;

public class Conversion {

	public static Vector2 dirToVec(boolean[] dirs) {
		if (dirs.length != 4)
			return null;
		
		Vector2 ret = new Vector2();
		if (dirs[0]) ret.y += 1f;
		if (dirs[1]) ret.x += 1f;
		if (dirs[2]) ret.y -= 1f;
		if (dirs[3]) ret.x -= 1f;
		
		if (ret.x != 0 && ret.y != 0) {
			ret.x *= 0.7071f;
			ret.y *= 0.7071f;
		}
		
		return ret;
	}
	
	public static Vector2 dirToVecWithMovespeed(boolean[] dirs, float movespeed) {
		Vector2 ret = dirToVec(dirs);
		if (ret != null) {
			ret.x *= movespeed;
			ret.y *= movespeed;	
		}
		
		return ret;
	}
}
