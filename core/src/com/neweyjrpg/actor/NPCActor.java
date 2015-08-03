package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.models.PhysicsModel;

public class NPCActor extends GameActor {
	
	public NPCActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys) {
		super(charaSet, pos, x, y, phys);
		this.movespeed = 2f;
		this.actionSpeed = 0.1f;
	}
}
