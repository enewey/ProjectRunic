package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.physics.BlockBody;

public class PlayerActor extends CharacterActor {
	
	private boolean isAttacking;
	public boolean isAttacking() { return this.isAttacking; }
	public void setAttacking(boolean b) { this.isAttacking = b; }
	
	public PlayerActor(Texture charaSet, int pos, float x, float y, BlockBody phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
	}
}
