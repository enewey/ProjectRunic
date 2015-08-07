package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neweyjrpg.models.PhysicsModel;

public class MassiveActor extends GameActor {

	private TextureRegion[][] graphics;
	private float gWidth, gHeight; //Height of each individual texture; must all be same size!
	
	public MassiveActor(float x, float y, PhysicsModel phys, TextureRegion[][] graphics, float gWidth, float gHeight) {
		super(x, y, phys);
		
		this.gWidth = gWidth;
		this.gHeight = gHeight;
		this.graphics = graphics;
	}

	@Override
	public void draw(Batch batch, float deltaTime) {
		for(int i=0; i < graphics.length; i++) {
			for(int j=0; j < graphics[i].length; j++) {
				batch.draw(graphics[i][j], getX() + (j * gWidth), getY() + (i * gHeight));
			}
		}
	}

	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
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
}
