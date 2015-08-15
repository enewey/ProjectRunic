package com.neweyjrpg.models;

import com.neweyjrpg.enums.Enums.Dir;

public class DirectionalInput extends Input {
	
	private boolean[] data;
	
	public DirectionalInput() {
		this.data = new boolean[4];
	}
	
	public void pushUp() 	{ this.data[0] = true; }
	public void pushRight() { this.data[1] = true; }
	public void pushDown() 	{ this.data[2] = true; }
	public void pushLeft() 	{ this.data[3] = true; }
	public void push(Dir d) {
		if (d == Dir.UP)
			this.data[0] = true;
		else if (d == Dir.RIGHT)
			this.data[1] = true;
		else if (d == Dir.DOWN)
			this.data[2] = true;
		else if (d == Dir.LEFT)
			this.data[3] = true;
	}
	
	public void liftUp() 	{ this.data[0] = false; }
	public void liftRight() { this.data[1] = false; }
	public void liftDown() 	{ this.data[2] = false; }
	public void liftLeft() 	{ this.data[3] = false; }
	public void lift(Dir d) {
		if (d == Dir.UP)
			this.data[0] = false;
		else if (d == Dir.RIGHT)
			this.data[1] = false;
		else if (d == Dir.DOWN)
			this.data[2] = false;
		else if (d == Dir.LEFT)
			this.data[3] = false;
	}
	
	public boolean isEmpty() {
		return !(data[0] || data[1] || data[2] || data[3]);
	}

}
