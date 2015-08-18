package com.neweyjrpg.models;

import com.badlogic.gdx.utils.Array;

public class Sequence<T> {
	
	protected Array<Step<T>> data;
	
	private int steps;
	protected int getSteps() { return this.steps; }
	private int stepMarker; //keeps track of no of steps before current step
	
	protected int pointer;
	
	private boolean looped;
	public boolean looped() { return this.looped; }
	protected void setLooped(boolean t) { this.looped = t; } 
	
	private boolean giveDuplicates;
	public void setDuplication(boolean t) { this.giveDuplicates = t; }
	
	public Sequence() {
		this.data = new Array<Step<T>>();
		this.steps = 0;
		this.looped = false;
		this.giveDuplicates = false;
	}
	
	public void add(T item, int repeats) {
		this.data.add(new Step<T>(item, repeats));
	}
	
	public void add(T[] arr, int repeats) {
		for (int i=0; i<arr.length; i++) {
			this.data.add(new Step<T>(arr[i], repeats));
		}
	}
	
	public T step() {
		T ret = null;
		if (this.data.get(pointer) != null) {
			this.steps++;
			if (steps - this.data.get(pointer).repeats > stepMarker) {
				stepMarker += this.data.get(pointer).repeats;
				ret = this.data.get(pointer).data;
				pointer++;
				if (pointer > data.size-1) {
					pointer = 0;
					looped = true;
				}
			}
			else if (giveDuplicates || !looped) {
				ret = this.data.get(pointer).data;
			}
		}
		return ret;
	}
}

class Step<T> {
	
	public T data;
	public int repeats;
	
	public Step(T data, int repeats) {
		this.data = data;
		this.repeats = repeats;
	}
}