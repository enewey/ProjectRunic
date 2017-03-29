package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.physics.BlockBody;

public class EnemyActor extends CharacterActor {

	public EnemyActor(Texture charaSet, int pos, float x, float y, BlockBody phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
	}

	@Override
	public void act(float delta) {
		if (!this.hasActions()) {

		}
		super.act(delta);
	}

}
