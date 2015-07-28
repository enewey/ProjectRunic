package com.neweyjrpg.controller;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class BadAIController implements IProducesInputs {

	private final static float TIME_BETWEEN_INPUTS = 0.25f;
	
	private float timeSinceLastInput;
	
	private boolean[] dirs;
	private boolean[] buttons;
	
	public BadAIController() {
		dirs = new boolean[]{false,false,false,false};
		buttons = new boolean[]{ false, false, false, false, false,
								 false, false, false, false, false};
		timeSinceLastInput = 0f;
	}
	
	@Override
	public DirectionalInput getDirectionalInput() {
		randomizeInputs();
		DirectionalInput input = new DirectionalInput();
		if (dirs[0]) input.pushUp();
		if (dirs[1]) input.pushRight();
		if (dirs[2]) input.pushDown();
		if (dirs[3]) input.pushLeft();
		return input;
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
			for (int i=0; i<4; i++)
				dirs[i] = false;
			for (int i=0; i<10; i++)
				buttons[i] = false;
			return;
		}
		else
			return;
		
		double rand = Math.random()*2 - 1;
		if (rand < -0.3)
			dirs[0] = true;
		else if (rand > 0.3)
			dirs[2] = true;
		
		rand = Math.random()*2 - 1;
		if (rand < -0.3)
			dirs[1] = true;
		else if (rand > 0.3)
			dirs[3] = true;
		//TODO: Add button randomization here -_-	
		
	}

}
