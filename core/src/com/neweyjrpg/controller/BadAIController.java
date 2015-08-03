package com.neweyjrpg.controller;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class BadAIController implements IProducesInputs {

	private float timeBetweenInputs;
	private float timeSinceLastInput;
	private DirectionalInput dirs;
	private boolean[] buttons;
	
	public BadAIController() {
		buttons = new boolean[10];
		dirs = new DirectionalInput();
		timeSinceLastInput = 0f;
		timeBetweenInputs = 1f;
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
		
		if (timeSinceLastInput >= timeBetweenInputs)
			timeSinceLastInput = 0f;
		else if (timeSinceLastInput >= timeBetweenInputs * 3 / 4) {
			dirs.clear();
			for (int i=0; i<buttons.length; i++)
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
