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
	
	public boolean[] getInputs() {
		return data;
	}
	
	public boolean isEmpty() {
		return !(data[0] || data[1] || data[2] || data[3]);
	}

}
