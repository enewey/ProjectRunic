package com.neweyjrpg.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public abstract class Manager implements IHandlesInteraction {
	
	public abstract boolean handleButtonPress(int button);
	public abstract boolean handleDirectionPress(int button);
	public abstract boolean handleButtonState(ButtonInput button);
	public abstract boolean handleDirectionState(DirectionalInput dir);
	public abstract void draw(float deltaTime, float offsetX, float offsetY, Batch batch);
	public abstract boolean act(float deltaTime);
	
}
