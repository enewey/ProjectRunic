package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.physics.BlockBody;

public class StaticActor extends GameActor {
	
	private TextureRegion texture;
	
	//Constructors
	public StaticActor(TextureRegion texture, float x, float y, BlockBody phys, Enums.Priority priority){
		super(x, y, phys, priority);
		this.texture = texture;
		this.phys = phys;
		this.actionSpeed = Constants.DEFAULT_ACTION_SPEED;
		this.setPosition(x, y); 
	}

	@Override
	public void draw(Batch batch, float deltaTime) {
		if (this.texture == null) return;
		super.draw(batch, deltaTime);
		batch.draw(texture, this.getX(), this.getY());
	}
	
	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
		if (this.texture == null) return;
		super.draw(batch, deltaTime, x, y);
		batch.draw(texture, x, y);
	}

//	@Override
//	public void move(float x, float y) {
//		this.addAction(Actions.moveBy(x, y, this.actionSpeed));
//	}
	
//	@Override
//	public void moveDistance(float x, float y, float scalar) {
//		this.addAction(Actions.moveBy(x*scalar, y*scalar, this.actionSpeed*scalar));
//	}

	@Override
	public Vector2 getSpriteSize() {
		return new Vector2(this.texture.getRegionWidth(), this.texture.getRegionHeight());
	}
	
	public void dispose() {
		this.texture.getTexture().dispose();
	}

	@Override
	public IProducesInputs getController() {
		return null;
	}
}
