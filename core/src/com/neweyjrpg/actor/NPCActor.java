package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;

public class NPCActor extends GameActor {
	
	public NPCActor(Texture charaSet, int pos, float x, float y) {
		super(charaSet, pos, x, y);
		this.movespeed = 1f;
	}

}
