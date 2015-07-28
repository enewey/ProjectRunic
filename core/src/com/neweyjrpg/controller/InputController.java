package com.neweyjrpg.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class InputController extends InputAdapter implements IProducesInputs {

	private boolean[] dirs;
	
	public InputController() {
		dirs = new boolean[4];
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Keys.UP:
				dirs[0]=false;
				return true;
			case Keys.RIGHT:
				dirs[1] = false;
				return true;
			case Keys.DOWN:
				dirs[2] = false;
				return true;
			case Keys.LEFT:
				dirs[3] = false;
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
			case Keys.UP:
				dirs[0] = true;
				return true;
			case Keys.RIGHT:
				dirs[1] = true;
				return true;
			case Keys.DOWN:
				dirs[2] = true;
				return true;
			case Keys.LEFT:
				dirs[3] = true;
				return true;
		}
		
		return false;
	}
	
	@Override
	public DirectionalInput getDirectionalInput() {
		DirectionalInput input = new DirectionalInput();
		if (dirs[0]) input.pushUp();
		if (dirs[1]) input.pushRight();
		if (dirs[2]) input.pushDown();
		if (dirs[3]) input.pushLeft();
		return input;
	}

	@Override
	public boolean[] getButtonInput() {
		return new boolean[10];
	}
	
	
}
