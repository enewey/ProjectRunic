package com.neweyjrpg.controller;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Keys;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class InputController implements IProducesInputs {

	private DirectionalInput dirs;
	private ButtonInput buttons;
	
	private LinkedList<Integer> inputQueue;
	public LinkedList<Integer> getQueue() { return this.inputQueue; }
	
	public InputController() {
		dirs = new DirectionalInput();
		buttons = new ButtonInput();
		inputQueue = new LinkedList<Integer>();
	}
	
	public int getButton(int keycode) {
		switch (keycode) {
		case Keys.Z:
			return 0;
		case Keys.X:
			return 1;
		case Keys.C:
			return 2;
		case Keys.A:
			return 3;
		case Keys.S:
			return 4;
		case Keys.D:
			return 5;
		case Keys.Q:
			return 6;
		case Keys.W:
			return 7;
		case Keys.E:
			return 8;
		case Keys.R:
			return 9;
		default:
			return -1;
		}
	}
	
	public int getDirection(int keycode) {
		switch (keycode) {
		case Keys.UP:
			return 0;
		case Keys.RIGHT:
			return 1;
		case Keys.DOWN:
			return 2;
		case Keys.LEFT:
			return 3;
		default:
			return -1;
		}
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
				inputQueue.addLast(keycode);
				return true;
			case Keys.RIGHT:
				dirs.pushRight();
				inputQueue.addLast(keycode);
				return true;
			case Keys.DOWN:
				dirs.pushDown();
				inputQueue.addLast(keycode);
				return true;
			case Keys.LEFT:
				dirs.pushLeft();
				inputQueue.addLast(keycode);
				return true;
			case Keys.Z:
				buttons.push(0);
				inputQueue.addLast(keycode);
				return true;
			case Keys.X:
				buttons.push(1);
				inputQueue.addLast(keycode);
				return true;
			case Keys.C:
				buttons.push(2);
				inputQueue.addLast(keycode);
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
