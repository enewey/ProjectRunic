package com.neweyjrpg.controller;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class BadAIController implements IProducesInputs {

	private final static float TIME_BETWEEN_INPUTS = 0.25f;
	
	private float timeSinceLastInput;
	
	DirectionalInput dirs;
	private boolean[] buttons;
	
	public BadAIController() {
		dirs = new DirectionalInput();
		buttons = new boolean[]{ false, false, false, false, false,
								 false, false, false, false, false};
		timeSinceLastInput = 0f;
	}
	
	@Override
	public DirectionalInput getDirectionalInput() {
		randomizeInputs();
		return dirs;
	}

	@Override
	public boolean[] getButtonInput() {
		randomizeInputs();
		return buttons;
	}
	
	private void randomizeInputs() {
		timeSinceLastInput += Gdx.graphics.getDeltaTime();
		
		if (timeSinceLastInput >= TIME_BETWEEN_INPUTS)
			timeSinceLastInput = 0f;
		else if (timeSinceLastInput >= TIME_BETWEEN_INPUTS * 3 / 4) {
			dirs.clear();
			for (int i=0; i<10; i++)
				buttons[i] = false;
			return;
		}
		else
			return;
		
		double rand = Math.random()*2 - 1;
		if (rand < -0.3)
			dirs.pushUp();
		else if (rand > 0.3)
			dirs.pushDown();
		
		rand = Math.random()*2 - 1;
		if (rand < -0.3)
			dirs.pushLeft();
		else if (rand > 0.3)
			dirs.pushRight();
		//TODO: Add button randomization here -_-	
		
	}

}
