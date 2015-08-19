package com.neweyjrpg.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameWindow {
	
	protected int width, height, x, y; //x and y are the bottom-left corner of window
	private TextureRegion windowSkin;
	
	
	public GameWindow(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		Pixmap pxm = new Pixmap(width, height, Format.RGB888);
		pxm.setColor(Color.BLACK);
		pxm.fillRectangle(x, y, width, height);
		
		this.windowSkin = new TextureRegion(new Texture(pxm));
		pxm.dispose();
	}
	
	public void draw(Batch batch, float deltaTime) {
		batch.draw(windowSkin, x, y);
	}

}
