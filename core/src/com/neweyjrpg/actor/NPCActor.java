package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.models.PhysicsModel;

public class NPCActor extends CharacterActor {
	
	public NPCActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys) {
		super(charaSet, pos, x, y, phys);
		this.movespeed = 1f;
		this.actionSpeed = 0.5f;
	}
}
