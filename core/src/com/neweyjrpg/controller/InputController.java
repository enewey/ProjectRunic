package com.neweyjrpg.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.DirectionalInput;

public class InputController extends InputAdapter {

	private boolean dirs[];
	
	public InputController() {
		dirs = new boolean[4];
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Keys.UP:
				dirs[0] = false;
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
	
	public boolean[] getDirs() {
		return dirs;
	}
//
//	@Override
//	public boolean[] getButtonInput() {
//		return new boolean[10];
//	}
	
	
}
