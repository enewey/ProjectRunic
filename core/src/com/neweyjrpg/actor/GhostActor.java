package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.enums.Enums.Priority;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.physics.BlockBody;

/**
 * An actor with no appearance.
 * Used to create blocks on map tiles.
 */
public class GhostActor extends GameActor {

	public GhostActor(float x, float y, BlockBody phys, Priority priority) {
		super(x, y, phys, priority);
	}

	@Override
	public void move(float x, float y) {
		this.setPosition(this.getX()+x, this.getY()+y);
	}
	
	@Override
	public void moveDistance(float x, float y, float speedScalar) {
		this.move(x*speedScalar, y*speedScalar);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public IProducesInputs getController() {
		return null;
	}

	@Override
	public void draw(Batch batch, float deltaTime, float offsetX, float offsetY) {
		
	}

	

}
