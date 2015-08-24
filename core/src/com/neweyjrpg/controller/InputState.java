package com.neweyjrpg.controller;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Keys;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class InputState implements IProducesInputs {

	private DirectionalInput dirs;
	private ButtonInput buttons;
	
	private LinkedList<Integer> inputQueue;
	public LinkedList<Integer> getQueue() { return this.inputQueue; }
	
	public InputState() {
		dirs = new DirectionalInput();
		buttons = new ButtonInput();
		inputQueue = new LinkedList<Integer>();
	}
	
	public boolean lift(int keycode) {
		int key = 0;
		if (InputState.getDirection(keycode) >= 0) {
			key = InputState.getDirection(keycode);
			this.dirs.lift(key);
			return true;
		}
		else if (InputState.getButton(keycode) >= 0) {
			key = InputState.getButton(keycode);
			this.buttons.lift(key);
			return true;
		}
		else
			return false;
	}
	
	public boolean push(int keycode) {
		inputQueue.addLast(keycode);
		int key = 0;
		if (InputState.getDirection(keycode) >= 0) {
			key = InputState.getDirection(keycode);
			this.dirs.push(key);
			return true;
		}
		else if (InputState.getButton(keycode) >= 0) {
			key = InputState.getButton(keycode);
			this.buttons.push(key);
			return true;
		}
		else
			return false;
	}
	
	@Override
	public DirectionalInput getDirectionalState() {
		return dirs;
	}

	@Override
	public ButtonInput getButtonState() {
		return buttons;
	}
	
	public static int getButton(int keycode) {
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
	
	public static int getDirection(int keycode) {
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
	
	public static boolean isDirection(int keycode) {
		return (getDirection(keycode) >= 0);
	}
	public static boolean isButton(int keycode) {
		return (getButton(keycode) >= 0);
	}
}
