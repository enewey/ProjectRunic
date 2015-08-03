package com.neweyjrpg.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.sun.media.sound.InvalidFormatException;

public class MapParser {

	private String[][] data;
	private String tileSet;
	
	public MapParser(String file)
	{
		BufferedReader br = null;
		try {
			FileHandle handle = Gdx.files.internal(file);
			br = handle.reader(1024);
			String[] dims = br.readLine().split("\\s+");

			int width = Integer.parseInt(dims[0]);
			int height = Integer.parseInt(dims[1]);
			
			tileSet = br.readLine();
			
			int counter = 0;
			String line = null;
			data = new String[height][];
			while ((line = br.readLine()) != null) {
				if (counter > height)
					throw new InvalidFormatException("Height does not match supplied map data");
				data[counter] = line.split(",");
				if (data[counter].length != width) {
					System.out.println("Invalid file format");
					throw new InvalidFormatException("Line length of map at line "+counter+" did not match dimension");
				}
				counter++;
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[][] getMapData(){
		return this.data;
	}
	public String getTileSetFile(){
		return this.tileSet;
	}
}
