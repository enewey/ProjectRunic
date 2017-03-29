package com.neweyjrpg.map;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums.TileBody;
import com.neweyjrpg.graphic.TileGraphic;
import com.neweyjrpg.models.PhysicsModel;

public class MapTile {
	
	TileGraphic graphic;
	PhysicsModel phys;
	
	public MapTile(Texture tileSet, String c, TileBody body) {
		if (c.length() != 4)
			System.out.println("Bad map tile creation; supplied tile string not of length 4");
		short dims = (short)Integer.parseInt(c, 16);
		if (dims == (short)0xFFFF) { //blank tile
			graphic = null;
		} else {
			int x = (dims & 0xFF00)>>8;
			int y = (dims & 0x00FF);
			graphic = new TileGraphic(tileSet, x, y);
		}
	}
	
	public MapTile(Texture tileSet, int x, int y) {
		graphic = new TileGraphic(tileSet, x, y);
	}
	
	public TileGraphic getGraphic(){
		return this.graphic;
	}
	
	public boolean isBlank() {
		return this.graphic == null;
	}
	
	public void dispose() {
		if (this.graphic != null) {
			this.graphic.getTexture().dispose();
		}
	}
	
	public PhysicsModel getPhysicsModel() {
		return this.phys;
	}
}
