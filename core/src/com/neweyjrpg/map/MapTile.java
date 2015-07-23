package com.neweyjrpg.map;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.graphic.TileGraphic;

public class MapTile {
	
	TileGraphic graphic;
	
	public MapTile(Texture tileSet, String c) {
		if (c.length() != 4)
			System.out.println("Bad map tile creation; supplied tile string not of length 2");
		short dims = Short.parseShort(c, 16);
		int x = (dims & 0xFF00)>>8;
		int y = (dims & 0x00FF);
		graphic = new TileGraphic(tileSet, x, y);
	}
	
	public MapTile(Texture tileSet, int x, int y) {
		graphic = new TileGraphic(tileSet, x, y);
	}
	
	public TileGraphic getGraphic(){
		return this.graphic;
	}
	
}
