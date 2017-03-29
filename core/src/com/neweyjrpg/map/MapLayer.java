package com.neweyjrpg.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums;

public class MapLayer {
	
	private int layer;
	private MapTile[][] data;
	private Texture tileSet;
	private HashMap<String, MapTile> tileCache;
	private Enums.Priority priority;
	
	public int dimY;
	public int dimX;
	
	public MapLayer(int layer, String tileFile, int width, int height, ArrayList<String> body, ArrayList<String> tile, Enums.Priority priority) {
		
		 tileCache = new HashMap<String, MapTile>();
		
		this.layer = layer;
		this.tileSet = new Texture(tileFile);
		this.priority = priority;
		
		this.data = new MapTile[width][height];
		
		for (int i = 0; i<tile.size(); i++)	{
			String[] tileRow = tile.get(i).split(",");
			String[] bodyRow = body.get(i).split(",");
			
			for (int j = 0; j<tileRow.length; j++) {
				if (!tileCache.containsKey(tileRow[j])){
					tileCache.put(tileRow[j], new MapTile(tileSet, tileRow[j], Enums.tileBody(bodyRow[j])));
				}
				data[j][i] = tileCache.get(tileRow[j]); //TODO: WHY ISN'T THIS WORKING?
			}
		}
		
		this.dimX = data.length;
		this.dimY = data[0].length;
	}
	
	public MapTile getTile(int x, int y) {
		return this.data[x][y];
	}
	
	public Enums.Priority getPriority() {
		return this.priority;
	}
	
	public void dispose() {
		for (int i=0; i < data.length; i++) {
			for (int j=0; j < data[i].length; j++) {
				data[i][j].dispose();
			}
		}
	}
	
}
