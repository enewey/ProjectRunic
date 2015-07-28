package com.neweyjrpg.models;

import java.util.Stack;

import com.neweyjrpg.enums.Enums.Dir;

public class DirectionalInput {
	
	private Stack<Dir> data;
	
	
	public DirectionalInput() {
		this.data = new Stack<Dir>();
	}
	public DirectionalInput(Dir[] in) {
		this.data = new Stack<Dir>();
		for (int i=0; i<in.length; i++)
			this.data.push(in[i]);
	}
	
	public void clear() {
		this.data.clear();
	}
	public boolean isEmpty() {
		return this.data.isEmpty();
	}
	public Dir pop() {
		return this.data.pop();
	}
	
	public void pushDown() 	{ this.push(Dir.DOWN); }
	public void pushUp() 	{ this.push(Dir.UP); }
	public void pushLeft() 	{ this.push(Dir.LEFT); }
	public void pushRight() { this.push(Dir.RIGHT); }
	
	private void push(Dir in) {
		if (!this.data.contains(in))
			this.data.push(in);
	}
	
	

}
