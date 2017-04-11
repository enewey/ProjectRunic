package com.neweyjrpg.models;

public class TemporalItem {

	public TemporalItem() {
		this.data = 0;
	}
	
	public TemporalItem(float n) {
		this.data = n;
	}
	
	private float data;
	public void activate(float num) { this.data = num; }
	
	public boolean isActive() { return this.data > 0; }
	public float getData() { return this.data; }
	
	public void tick(float delta) {
		this.data -= delta;
		if (this.data < 0)
			this.data = 0;
	}
	
	public void increment(float delta) {
		this.data += delta;
	}	
}
