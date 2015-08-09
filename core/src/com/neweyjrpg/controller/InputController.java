package com.neweyjrpg.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class InputController implements IProducesInputs {

	private DirectionalInput dirs;
	
	public InputController() {
		dirs = new DirectionalInput();
	}
	
	public boolean keyUp(int keycode) {
		switch (keycode){
		case Keys.UP:
			dirs.liftUp();
			return true;
		case Keys.RIGHT:
			dirs.liftRight();
			return true;
		case Keys.DOWN:
			dirs.liftDown();
			return true;
		case Keys.LEFT:
			dirs.liftLeft();
			return true;
		}
	
		return false;
	}
	
	public boolean keyDown(int keycode) {
		switch (keycode){
			case Keys.UP:
				dirs.pushUp();
				return true;
			case Keys.RIGHT:
				dirs.pushRight();
				return true;
			case Keys.DOWN:
				dirs.pushDown();
				return true;
			case Keys.LEFT:
				dirs.pushLeft();
				return true;
		}
		
		return false;
	}

	@Override
	public DirectionalInput getDirectionalInput() {
		return dirs;
	}

	@Override
	public boolean[] getButtonInput() {
		// TODO Auto-generated method stub
		return null;
	}	
}
