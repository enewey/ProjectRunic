package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.models.PhysicsModel;

public class MassiveActor extends GameActor {

	private TextureRegion[][] graphics;
	private float gWidth, gHeight; //Height of each individual texture; must all be same size!
	private float totalWidth, totalHeight;
	
	public MassiveActor(float x, float y, PhysicsModel phys, TextureRegion[][] graphics, float gWidth, float gHeight) {
		super(x, y, phys);
		
		this.gWidth = gWidth;
		this.gHeight = gHeight;
		this.graphics = graphics;
		
		totalWidth = 0; 
		totalHeight= graphics.length * gHeight;
		for (int i=0; i < graphics.length; i++) {
			if (graphics[i].length * gWidth > totalWidth)
				totalWidth = graphics[i].length * gWidth;
		}
	}

	@Override
	public void draw(Batch batch, float deltaTime) {
		super.draw(batch, deltaTime);
		for(int i=0; i < graphics.length; i++) {
			for(int j=0; j < graphics[i].length; j++) {
				batch.draw(graphics[i][j], getX() + (j * gWidth), getY() + (i * gHeight));
			}
		}
	}

	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
		super.draw(batch, deltaTime, x, y);
		for(int i=0; i < graphics.length; i++) {
			for(int j=0; j < graphics[i].length; j++) {
				batch.draw(graphics[i][j], x + (j * gWidth), y + (i * gHeight));
			}
		}
	}

	@Override
	public void move(float x, float y) {
		this.setPosition(getX()+x, getY()+y);
	}
	
	@Override
	public Vector2 getSpriteSize() {
		// TODO Auto-generated method stub
		return new Vector2(totalWidth, totalHeight);
	}
}
