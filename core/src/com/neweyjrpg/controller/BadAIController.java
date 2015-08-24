package com.neweyjrpg.controller;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class BadAIController implements IProducesInputs {

	private float timeBetweenInputs;
	private float timeSinceLastInput;
	private DirectionalInput dirs;
	private ButtonInput buttons;
	
	public BadAIController() {
		buttons = new ButtonInput();
		dirs = new DirectionalInput();
		timeSinceLastInput = 0f;
		timeBetweenInputs = 1f;
	}
	
	@Override
	public DirectionalInput getDirectionalState() {
		randomizeInputs();
		return dirs;
	}

	@Override
	public ButtonInput getButtonState() {
		randomizeInputs();
		return buttons;
	}
	
	private void randomizeInputs() {
		timeSinceLastInput += Gdx.graphics.getDeltaTime();
		
		if (timeSinceLastInput >= timeBetweenInputs)
			timeSinceLastInput = 0f;
		else if (timeSinceLastInput >= timeBetweenInputs * 3 / 4) {
			dirs.clear();
			buttons.clear();
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
