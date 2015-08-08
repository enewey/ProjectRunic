package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.models.PhysicsModel;

public class EnemyActor extends CharacterActor {

	public EnemyActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys) {
		super(charaSet, pos, x, y, phys);
	}
	
	@Override
	public void act(float delta) {
		if (!this.hasActions()) {
			
		}
		super.act(delta);
	}

}
