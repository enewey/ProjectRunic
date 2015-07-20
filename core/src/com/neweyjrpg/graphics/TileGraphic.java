package com.neweyjrpg.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileGraphic extends TextureRegion {
	
	public TileGraphic(Texture tileSet, int x, int y)
	{
		super(tileSet, x*17, y*17, 16, 16);
	}

}
