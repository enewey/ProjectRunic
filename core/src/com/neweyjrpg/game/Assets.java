package com.neweyjrpg.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	
	public static HashMap<String, BitmapFont> fonts;
	public static BitmapFont loadFont(String filename) {
		if (fonts == null) {
			fonts = new HashMap<String, BitmapFont>();
		}
		
		if (fonts.containsKey(filename)) {
			return fonts.get(filename);
		}

		BitmapFont f = new BitmapFont(Gdx.files.internal(filename));
		fonts.put(filename, f);
		
		
		
		return f;
	}	
}
