package com.neweyjrpg.controller;

import com.badlogic.gdx.Input.Keys;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class InputController implements IProducesInputs {

	private DirectionalInput dirs;
	private ButtonInput buttons;
	
	public InputController() {
		dirs = new DirectionalInput();
		buttons = new ButtonInput();
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
		case Keys.Z:
			buttons.lift(0);
			return true;
		case Keys.X:
			buttons.lift(1);
			return true;
		case Keys.C:
			buttons.lift(2);
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
			case Keys.Z:
				buttons.push(0);
				return true;
			case Keys.X:
				buttons.push(1);
				return true;
			case Keys.C:
				buttons.push(2);
				return true;
		}
		
		return false;
	}

	@Override
	public DirectionalInput getDirectionalInput() {
		return dirs;
	}

	@Override
	public ButtonInput getButtonInput() {
		return buttons;
	}	
}
