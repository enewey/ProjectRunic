package com.neweyjrpg.controller;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.interfaces.IProducesInputs;

public class BadAIController implements IProducesInputs {

	private final static float TIME_BETWEEN_INPUTS = 0.25f;
	
	private float timeSinceLastInput;
	
	private boolean[] directions;
	private boolean[] buttons;
	
	public BadAIController() {
		directions = new boolean[]{ false, false, false, false};
		buttons = new boolean[]{ false, false, false, false, false,
								 false, false, false, false, false};
		timeSinceLastInput = 0f;
	}
	
	@Override
	public boolean[] getDirectionalInput() {
		randomizeInputs();
		return directions;
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
		else if (timeSinceLastInput >= TIME_BETWEEN_INPUTS / 2){
			for (int i=0; i<4; i++)
				directions[i] = false;
			for (int i=0; i<10; i++)
				buttons[i] = false;
			return;
		}
		else
			return;
		
		for (int i=0; i<4; i++) {
			if (Math.random() >= 0.5) {
				directions[i] = false;
			} else {
				directions[i] = true;
			}
		}
		if (directions[0] && directions[2]) {
			if (Math.round(Math.random()) == 0) directions[0] = false;
			else directions[2] = false;
		}
		if (directions[1] && directions[3]) {
			if (Math.round(Math.random()) == 0) directions[1] = false;
			else directions[3] = false;
		}
			
		//TODO: Add button randomization here -_-	
		
	}

}
