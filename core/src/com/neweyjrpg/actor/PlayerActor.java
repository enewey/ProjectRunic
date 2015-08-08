package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.models.PhysicsModel;

public class PlayerActor extends CharacterActor {
	
	public PlayerActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys) {
		super(charaSet, pos, x, y, phys);
	}
}
