package com.neweyjrpg.models;

import java.util.Stack;

import com.neweyjrpg.enums.Enums.Dir;

public class DirectionalInput {
	
	private boolean[] data;
	
	public DirectionalInput() {
		this.data = new boolean[4];
	}
	
	public void pushUp() 	{ this.data[0] = true; }
	public void pushRight() { this.data[1] = true; }
	public void pushDown() 	{ this.data[2] = true; }
	public void pushLeft() 	{ this.data[3] = true; }
	
	public void clear() {
		for (int i=0; i<4; i++)
			this.data[i] = false;
	}
	
	public Stack<Dir> getInputs() {
		Stack<Dir> ret = new Stack<Dir>();
		if (data[0]) ret.push(Dir.UP);
		if (data[1]) ret.push(Dir.RIGHT);
		if (data[2]) ret.push(Dir.DOWN);
		if (data[3]) ret.push(Dir.LEFT);
		return ret;
	}

}
