package com.neweyjrpg.util;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums.Dir;

public class Conversion {

	public static Vector2 dpadToVec(boolean[] dirs) {
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
	
	public static Vector2 dpadToVecWithMovespeed(boolean[] dirs, float movespeed) {
		Vector2 ret = dpadToVec(dirs);
		if (ret != null) {
			ret.x *= movespeed;
			ret.y *= movespeed;	
		}
		
		return ret;
	}
	
	public static Vector2 dirToVec(Dir dir) {
		Vector2 v = new Vector2(0,0);
		switch (dir) {
		case UP:
			v.y = 1.0f;
			break;
		case DOWN:
			v.y = -1.0f;
			break;
		case LEFT:
			v.x = -1.0f;
			break;
		case RIGHT:
			v.x = 1.0f;
			break;
		}
		return v;
	}
}
