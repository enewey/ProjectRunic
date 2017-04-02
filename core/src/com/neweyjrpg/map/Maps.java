package com.neweyjrpg.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.TileBody;
import com.neweyjrpg.physics.BlockBody;

public class Maps {
	
	/**
	 * This is hideous.
	 * Parses a list of MapLayers from a file.
	 * @param file
	 * @return the list of MapLayers.
	 */
	public static ArrayList<MapLayer> parseMap(String file)
	{
		ArrayList<MapLayer> ret = null;
		
		BufferedReader br = null;
		ArrayList<String> input = new ArrayList<String>();
		String tileSet;
		
		//Actual parsing from file
		try {
			FileHandle handle = Gdx.files.internal(file);
			br = handle.reader(1024);	
			String line = null;
			while ((line = br.readLine()) != null) {
				input.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Format
		//1st line - Layers, and then Map dimensions, space separated
		String[] dims = input.get(0).split("\\s+");
		int layers = Integer.parseInt(dims[0]);
		ret = new ArrayList<MapLayer>(layers);
		for (int p=0; p<layers; p++) {
			ret.add(null);
		}
		int width = Integer.parseInt(dims[1]);
		int height = Integer.parseInt(dims[2]);
		
		//2nd line - Tileset filename
		tileSet = input.get(1);
		
		int[] bodyRange = new int[2];
		int[] tileRange = new int[2];
		int layer = -1;
		Enums.Priority priority = Enums.Priority.Below;
		//Then, layers start with a "start layer"
		for (int i=2; i<input.size(); i++) {
			
			if (input.get(i).startsWith("layer")) {
				String[] sp = input.get(i).split("\\s+");
				if (sp[1].equals("end")) {
					//Layer creation
					ArrayList<String> body = new ArrayList<String>(input.subList(bodyRange[0], bodyRange[1]));
					ArrayList<String> tile = new ArrayList<String>(input.subList(tileRange[0], tileRange[1]));
					ret.set(layer, new MapLayer(layer, tileSet, width, height, body, tile, priority));
					
				} else {
					layer = Integer.parseInt(sp[1]);
					priority = Enums.priority(sp[2]);
				}
				
			} else if (input.get(i).startsWith("start")) {
				String[] sp = input.get(i).split("\\s+");
				if (sp[1].equals("body")) {
					bodyRange[0] = i+1;
				} else if (sp[1].equals("tile")) {
					tileRange[0] = i+1;
				}
			} else if (input.get(i).startsWith("end")) {
				String[] sp = input.get(i).split("\\s+");
				if (sp[1].equals("body")) {
					bodyRange[1] = i;
				} else if (sp[1].equals("tile")) {
					tileRange[1] = i;
				}
			}
			
		}
		
		
		return ret;
	}
	
	public static BlockBody bodyFromTile(TileBody body, int x, int y) {
		switch (body) {
		case Block:
			return new BlockBody(Enums.PhysicalState.StaticBlock, 
					new Rectangle(x*Constants.TILE_WIDTH + 1, y*Constants.TILE_HEIGHT + 1, Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
		case Circle:
			return new BlockBody(Enums.PhysicalState.StaticBlock, 
					new Rectangle((x+2)*Constants.TILE_WIDTH, (y+2)*Constants.TILE_HEIGHT, Constants.TILE_WIDTH-4, Constants.TILE_HEIGHT-4));
		case Open:
		default:
			return null;
		}
	}
}
