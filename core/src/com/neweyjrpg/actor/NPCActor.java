package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.interfaces.IProducesInputs;

public class NPCActor extends GameActor {
	
	public NPCActor(Texture charaSet, int pos, float x, float y) {
		super(charaSet, pos, x, y);
		this.movespeed = 2f;
		this.actionSpeed = 0.1f;
	}
}
