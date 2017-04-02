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
	private TextureRegion windowSkin;
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
		Pixmap pxm = new Pixmap(width, height, Format.RGB888);
		pxm.setColor(new Color(0,0,0,1f));
		pxm.fillRectangle(x, y, width, height);
		
		this.windowSkin = new TextureRegion(new Texture(pxm));
		pxm.dispose();
		
		this.interaction = i;
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
