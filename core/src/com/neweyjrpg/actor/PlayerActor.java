package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.models.PhysicsModel;

public class PlayerActor extends CharacterActor {
	
	public PlayerActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
	}
}
