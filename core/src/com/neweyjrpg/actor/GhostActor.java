package com.neweyjrpg.actor;

import com.badlogic.gdx.math.Vector2;
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
		
	}

	@Override
	public Vector2 getSpriteSize() {
		return new Vector2(0,0);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public IProducesInputs getController() {
		return null;
	}

}
