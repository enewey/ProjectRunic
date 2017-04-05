package com.neweyjrpg.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;

public class MapLayer {
	
	//TODO: Refactor this goddamn mess!!
	private float fboScalar = 2.0f;
	public float regionX;
	public float regionY;
	private FrameBuffer fbo;
	private Matrix4 ortho;
	public Matrix4 getProjectionMatrix() { return this.ortho; }
	public void writeLayerToTexture() {
		if (fbo == null) {
			this.regionX = this.dimX * Constants.TILE_WIDTH;
			this.regionY = this.dimY * Constants.TILE_HEIGHT;
			fbo = new FrameBuffer(Format.RGBA8888, 
					(int)(this.regionX * fboScalar), 
					(int)(this.regionY * fboScalar), 
					false);
		}
		this.entireLayer = new TextureRegion(fbo.getColorBufferTexture());
		this.entireLayer.flip(false, true);
		fbo.begin();
		
		SpriteBatch newBatch = new SpriteBatch();
		newBatch.begin();
		this.ortho = new Matrix4();
		ortho.setToOrtho2D(0, 0, this.dimX*Constants.TILE_WIDTH,this.dimY*Constants.TILE_HEIGHT);
		newBatch.setProjectionMatrix(ortho);
		for (int x=0; x<this.dimX; x++) {
			for (int y=0; y<this.dimY; y++) {
				MapTile tile = this.getTile(x, y);
				if (!tile.isBlank()) {
					newBatch.draw(tile.getGraphic(), x*Constants.TILE_WIDTH, y*Constants.TILE_HEIGHT);
				}
			}
		}
		newBatch.end();		
		fbo.end();
		this.entireLayer.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	
	private int layer;
	public int getLayer() { return this.layer; }
	
	private MapTile[][] data;
	private Texture tileSet;
	private HashMap<String, MapTile> tileCache;
	
	private Enums.Priority priority;
	//if this is a BELOW or ABOVE layer, then we will draw the entire map to a texture using a frame buffer object.
	private TextureRegion entireLayer;
	public TextureRegion getEntireLayer() { return this.entireLayer; }
	
	public int dimY;
	public int dimX;
	
	public MapLayer(int layer, String tileFile, int width, int height, ArrayList<String> body, ArrayList<String> tile, Enums.Priority priority) {
		entireLayer = null;
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
				data[j][i] = tileCache.get(tileRow[j]);
			}
		}
		
		this.dimX = data.length;
		this.dimY = data[0].length;
		
		if (this.priority == Enums.Priority.Above || this.priority == Enums.Priority.Below) {
			this.writeLayerToTexture();
		}
	}
	
	public MapTile getTile(int x, int y) {
		return this.data[x][y];
	}
	
	public MapTile[][] getData() {
		return this.data;
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
