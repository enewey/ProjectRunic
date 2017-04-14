package com.neweyjrpg.util;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.game.Assets;

public class Funcs {

	public static Vector2 roundPixels(float x, float y) {
		int xfloor = (int)Math.floor(x * 10); //truncate to one decimal
		xfloor = xfloor - (xfloor % 2); //round decimal to nearest even number
		int yfloor = (int)Math.floor(y * 10);
		yfloor = yfloor - (yfloor % 2);
		
		return new Vector2((float)xfloor / 10.0f, (float)yfloor / 10.0f);
	}
	
	// ---- Vector Math
	
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
	
	// ----
	
	// ---- String manipulation
	
	public static ArrayList<String> formatString(String str, String font, int windowWidth, int padding) {
		GlyphLayout layout 			= new GlyphLayout();
		BitmapFont f 				= Assets.loadFont(font);
		String[] split 				= str.split("\\s+");
		ArrayList<String> ret 			= new ArrayList<String>();
		StringBuilder builder 		= new StringBuilder("");
		StringBuilder lineBuilder 	= new StringBuilder("");
		
		for (int i=0, k=0; i<split.length; i++) {
			layout.setText(f, builder.toString() + " " + split[i]);
			if (layout.width + (padding * 2) > windowWidth) {
				lineBuilder.append(builder.toString() + "\n");
				builder.delete(0, builder.length());
				if (++k > 3) {
					ret.add(lineBuilder.toString());
					lineBuilder.delete(0, lineBuilder.length());
					k = 0;
				}
			}
			builder.append(" " + split[i]);
		}
		lineBuilder.append(builder.toString());
		ret.add(lineBuilder.toString());
		return ret;
	}
	
	public static int getWindowHeight(String str, String font) {
		ArrayList<String> lines = Funcs.formatString(str, font, Constants.GAME_WIDTH/3, Constants.WINDOW_PADDING);
		if (lines.size() > 1) {
			return lines.get(0).split("\\n").length * 20;
		} else {
			return 80;
		}
	}
}
