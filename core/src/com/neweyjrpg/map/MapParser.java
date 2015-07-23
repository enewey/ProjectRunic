package com.neweyjrpg.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapParser {

	public MapParser(String file)
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String[] dims = br.readLine().split("\\s+");

			int width = Integer.parseInt(dims[0]);
			int height = Integer.parseInt(dims[1]);
			
			String tileSet = br.readLine();
			
			int counter = 0;
			String line = null;
			char[][] data = new char[height][];
			while ((line = br.readLine()) != null && counter < height) {
				data[counter++] = line.toCharArray();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
