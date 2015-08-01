package com.neweyjrpg.map;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class GameMap {

	private HashMap<String, MapTile> tileCache;
	private Texture tileSet;
	private MapTile[][] mapData;
	private int dimX, dimY;
	public int getDimX() { return dimX; }
	public int getDimY() { return dimY; }
	
	public GameMap(String tileFile, String mapFile) {
		MapParser mapParser = new MapParser(mapFile);
		String[][] data = mapParser.getMapData();
		tileSet = new Texture(tileFile);
		dimX = data.length;
		dimY = data[0].length;
		mapData = new MapTile[dimY][dimX];
		tileCache = new HashMap<String, MapTile>();
		
		for (int i = 0; i<data.length; i++)	{
			for (int j = 0; j<data[i].length; j++) {
				if (!tileCache.containsKey(data[i][j])){
					tileCache.put(data[i][j], new MapTile(tileSet, data[i][j]));
				}
				mapData[j][i] = tileCache.get(data[i][j]);
			}
		}		
	}
	
	public MapTile[][] getMapData(){
		return this.mapData;
	}
	
	public MapTile getMapTile(int x, int y) {
		return this.mapData[x][y];
	}
}
