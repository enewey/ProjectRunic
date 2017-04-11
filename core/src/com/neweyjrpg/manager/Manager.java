package com.neweyjrpg.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public abstract class Manager implements IHandlesInteraction {
	
	public abstract boolean handleButtonPress(int button);
	public abstract boolean handleDirectionPress(int button);
	public abstract boolean handleButtonState(ButtonInput button);
	public abstract boolean handleDirectionState(DirectionalInput dir);
	public abstract void draw(float deltaTime, int yaxis, float offsetX, float offsetY, Batch batch, Enums.Priority priority);
	public abstract boolean act(float deltaTime);
	public abstract void dispose();
	public abstract void block();
	public abstract void unblock();
	
}
