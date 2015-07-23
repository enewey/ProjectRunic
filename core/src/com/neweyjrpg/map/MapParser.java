package com.neweyjrpg.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.sun.media.sound.InvalidFormatException;

public class MapParser {

	private char[][] data;
	private String tileSet;
	
	@SuppressWarnings("resource")
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
			data = new char[height][];
			while ((line = br.readLine()) != null && counter < height) {
				data[counter] = line.toCharArray();
				if (data[counter].length != width) {
					System.out.println("Line length invalid");
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
	
	public char[][] getMapData(){
		return this.data;
	}
	public String getTileSetFile(){
		return this.tileSet;
	}
}
