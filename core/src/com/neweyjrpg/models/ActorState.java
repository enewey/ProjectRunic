package com.neweyjrpg.models;

public class ActorState {

	private float duration;
	public void activate(float duration) { this.duration = duration; }
	public boolean isActive() { return this.duration > 0; }
	public void tick(float delta) {
		this.duration -= delta;
		if (this.duration < 0) 
			this.duration = 0;
	}
	
}
