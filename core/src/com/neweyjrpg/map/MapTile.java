package com.neweyjrpg.map;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.graphic.TileGraphic;

public class MapTile {
	
	TileGraphic graphic;
	
	public MapTile(Texture tileSet, char c) {
		int x = (int)(c / 10);
		int y = (int)(c % 10);
		graphic = new TileGraphic(tileSet, x, y);
	}
	
	public TileGraphic getGraphic(){
		return this.graphic;
	}
	
}
