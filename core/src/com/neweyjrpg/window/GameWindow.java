package com.neweyjrpg.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.interaction.Interaction;

public abstract class GameWindow {
	
	protected int width, height, x, y; //x and y are the bottom-left corner of window
	protected TextureRegion windowSkin;
	private boolean disposed;
	public boolean isDisposed() { return this.disposed; }
	private Enums.Priority priority;
	public Enums.Priority getPriority() { return this.priority; }
	
	private Interaction interaction;
	
	public GameWindow(Interaction i, int x, int y, int width, int height, Enums.Priority priority) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		
		this.interaction = i;
		this.priority = priority;
	}
	
	protected void setSkin() {
		Pixmap pxm = new Pixmap(width, height, Format.RGB888);
		pxm.setColor(Color.BLACK);
		pxm.fillRectangle(0, 0, width, height);
		pxm.setColor(Color.WHITE);
		pxm.drawRectangle(1, 1, width-2, height-2);
		
		this.windowSkin = new TextureRegion(new Texture(pxm));
		pxm.dispose();
	}
	
	public void draw(Batch batch, float deltaTime) {
		batch.setColor(Color.WHITE);
		batch.draw(windowSkin, x, y);
	}
	
	public void dispose() {
		this.windowSkin.getTexture().dispose();
		this.interaction.complete();
		this.disposed = true;
	}
	
	public abstract void interact();
}
