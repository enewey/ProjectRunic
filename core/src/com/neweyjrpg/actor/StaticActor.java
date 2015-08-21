package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.neweyjrpg.models.PhysicsModel;

public class StaticActor extends GameActor {
	
	private TextureRegion texture;
	
	//Constructors
	public StaticActor(TextureRegion texture, float x, float y, PhysicsModel phys){
		super(x, y, phys);
		this.texture = texture;
		this.phys = phys;
		this.setPosition(x, y); 
	}

	@Override
	public void draw(Batch batch, float deltaTime) {
		super.draw(batch, deltaTime);
		batch.draw(texture, this.getX(), this.getY());
	}
	
	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
		batch.draw(texture, x, y);
	}

	@Override
	public void move(float x, float y) {
		this.addAction(Actions.moveBy(x, y, 0.25f));
	}

	@Override
	public Vector2 getSpriteSize() {
		return new Vector2(this.texture.getRegionWidth(), this.texture.getRegionHeight());
	}
}
