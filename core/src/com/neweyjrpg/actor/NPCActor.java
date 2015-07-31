package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.interfaces.IProducesInputs;

public class NPCActor extends GameActor {
	
	IProducesInputs ai;
	public void setAi(IProducesInputs ai) {	this.ai = ai; }
	
	public NPCActor(Texture charaSet, int pos, float x, float y) {
		super(charaSet, pos, x, y);
		this.movespeed = 2f;
		this.actionSpeed = 0.1f;
	}
	
	@Override
	public void act(float delta) {
		this.dirs = ai.getDirectionalInput().getInputs();
		super.act(delta);
	}
}
