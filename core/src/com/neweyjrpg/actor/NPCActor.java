package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.models.PhysicsModel;

public class NPCActor extends CharacterActor {
	
	public NPCActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
		this.movespeed = 1f;
		this.actionSpeed = 0.5f;
	}
}
