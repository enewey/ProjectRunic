package com.neweyjrpg.map;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.actor.GhostActor;
import com.neweyjrpg.actor.StaticActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.physics.BlockBody;

public class GameMap {

	private ArrayList<MapLayer> mapData = null;
	private int dimX, dimY;
	public int getDimX() { 
		if (this.dimX == 0) {
			this.dimX = mapData.get(0).dimX;
		}
		return this.dimX; 
	}
	public int getDimY() { 
		if (this.dimY == 0) {
			this.dimY = mapData.get(0).dimY;
		}
		return this.dimY; 
	}
	
	public GameMap(String mapFile) {
		mapData = Maps.parseMap(mapFile);				
	}
	
	public ArrayList<MapLayer> getMapData(){
		return this.mapData;
	}
	
	public ArrayList<GhostActor> getBlocks() {
		ArrayList<GhostActor> blocks = new ArrayList<GhostActor>();
		for (MapLayer layer : mapData) {
			for (int x=0; x<layer.getData().length; x++) {
				for (int y=0; y<layer.getData()[0].length; y++) {
					BlockBody block = Maps.bodyFromTile(layer.getData()[x][y].body, x, y);
					if (block != null) {
						blocks.add(new GhostActor(block.getBounds().x, block.getBounds().y, block, Enums.Priority.Below));
					}
				}
			}
		}
		
		return blocks;
	}
	
	public MapTile[] getTileLayers(int x, int y) {
		MapTile[] ret = new MapTile[this.mapData.size()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.mapData.get(0).getTile(x,y);
		}
		return ret;
	}
	
	public void draw(Batch batch, int yaxis, float deltaTime, float offsetX, float offsetY, Enums.Priority priority) {
		int startX = Math.max((int)Math.floor(-offsetX/Constants.TILE_WIDTH), 0),
			startY = Math.max((int)Math.floor(-offsetY/Constants.TILE_HEIGHT), 0);
		int endX = Math.min(startX + (int)Math.round((Constants.GAME_WIDTH / Constants.TILE_WIDTH)+1), this.dimX),
			endY = Math.min(startY + (int)Math.round((Constants.GAME_HEIGHT / Constants.TILE_HEIGHT)+1), this.dimX);
		
		if (yaxis < startY && yaxis > endY) {
			return;
		}
		
		for (int i=0; i<mapData.size(); i++) {
			MapLayer layer = mapData.get(i);
			if (layer.getPriority() != priority) { 
				continue; 
			}
			for (int x=startX; x<endX; x++) {
				MapTile tile = layer.getTile(x, yaxis);
				if (!tile.isBlank()) {
					batch.draw(tile.getGraphic(), offsetX+(x*16), offsetY+(yaxis*16));
				}
			}
		}
		
	}
	
	public void dispose() {
		for (int i=0; i < mapData.size(); i++) {
			mapData.get(i).dispose();
		}
	}
}
