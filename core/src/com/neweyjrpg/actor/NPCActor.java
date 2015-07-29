package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class NPCActor extends GameActor {
	
	public NPCActor(World world, Texture charaSet, int pos, float x, float y) {
		super(world, charaSet, pos, x, y);
		this.movespeed = 1f;
	}
}
