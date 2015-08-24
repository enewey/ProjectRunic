package com.neweyjrpg.models;

import com.neweyjrpg.enums.Enums.Dir;

public class DirectionalInput extends Input {
	
	public DirectionalInput() {
		this.data = new boolean[4];
		this.time = new float[4];
	}
	
	public void pushUp() 	{ this.data[0] = true; 		this.time[0] = System.currentTimeMillis(); }
	public void pushRight() { this.data[1] = true; 		this.time[1] = System.currentTimeMillis(); }
	public void pushDown() 	{ this.data[2] = true; 		this.time[2] = System.currentTimeMillis(); }
	public void pushLeft() 	{ this.data[3] = true; 		this.time[3] = System.currentTimeMillis(); }
	public void push(Dir d) {
		if (d == Dir.UP)
			this.pushUp();
		else if (d == Dir.RIGHT)
			this.pushRight();
		else if (d == Dir.DOWN)
			this.pushDown();
		else if (d == Dir.LEFT)
			this.pushLeft();
	}
	public void push(int d) { this.data[d] = true;		this.time[d] = System.currentTimeMillis(); }
	
	public void liftUp() 	{ this.data[0] = false; 	this.time[0] = 0; }
	public void liftRight() { this.data[1] = false;		this.time[1] = 0; }
	public void liftDown() 	{ this.data[2] = false;		this.time[2] = 0; }
	public void liftLeft() 	{ this.data[3] = false;		this.time[3] = 0; }
	public void lift(Dir d) {
		if (d == Dir.UP)
			this.liftUp();
		else if (d == Dir.RIGHT)
			this.liftRight();
		else if (d == Dir.DOWN)
			this.liftDown();
		else if (d == Dir.LEFT)
			this.liftLeft();
	}
	public void lift(int d) { this.data[d] = false;		this.time[d] = 0; }
	
	public boolean isEmpty() {
		return !(data[0] || data[1] || data[2] || data[3]);
	}

}
