package com.neweyjrpg.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.interfaces.IHandlesInteraction;

public abstract class Manager implements IHandlesInteraction {
	
	public abstract boolean handleButtonPress(int button);
	public abstract void draw(float deltaTime, float offsetX, float offsetY, Batch batch);
	public abstract boolean act(float deltaTime);
	
}
