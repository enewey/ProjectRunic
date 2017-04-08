package com.neweyjrpg.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.interaction.Interaction;

public class PopupWindow extends GameWindow {

	private Vector2 source;
	
	private float duration;
	private float elapsed;
	
	public PopupWindow(Interaction i, int x, int y, int width, int height, Vector2 source, float duration) {
		super(i, x, y, width, height, Enums.Priority.Above);
		this.setSkin();
		this.source = source;
		this.duration = duration;
		this.elapsed = 0f;
	}
	
	@Override
	protected void setSkin() {
		Pixmap pxm = new Pixmap(width, height, Format.RGBA8888);
		pxm.setColor(Color.BLACK);
		pxm.drawRectangle(x, y, width, height);
		
		this.windowSkin = new TextureRegion(new Texture(pxm));
		pxm.dispose();
	}
	
	public boolean isDone() {
		return (elapsed / duration >= 1);
	}
	
	private float delta() {
		return elapsed / duration;
	}
	
	private Vector2 calculateOffset() {
		float interp = (this.source.y - this.y) / (this.source.x - this.x);
		float offsetX = this.source.x + ((this.x - this.source.x) * this.delta());
		float offsetY = (interp * offsetX) - (interp * this.x) + (this.y);
		return new Vector2(offsetX, offsetY);
	}
	
	@Override
	public void draw(Batch batch, float deltaTime) {
		this.elapsed += 0.1f;
		
		if (this.isDone()) {
			this.dispose();
			return;
		}
		
		batch.setColor(Color.WHITE);
		Vector2 v = calculateOffset();
		batch.draw(this.windowSkin, v.x, v.y, this.width * this.delta(), this.height * this.delta());
	}

	@Override
	public void interact() {
		this.elapsed = this.duration;
		this.dispose();
	}

}
