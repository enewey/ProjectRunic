package com.neweyjrpg.interaction;

import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.Manager;

public abstract class Interaction {
	private IHandlesInteraction scene;
	protected boolean started;
	protected boolean completed;
	public Interaction(IHandlesInteraction scene) {
		this.scene = scene;
		this.init();
	} 
	public void setScene(IHandlesInteraction scene) { this.scene = scene; }
	
	public abstract Object getData();
	public Interaction process(Manager m) {
		this.started = true;
		return this;
	}
	public void init() {
		this.started = false;
		this.completed = false;
	}
	public boolean isStarted() { return this.started; }
	public boolean isCompleted() { return this.completed; }
	public void complete() {
		this.completed = true;
		if (scene != null) {
			this.scene.onInteractionComplete(this);
		}
	}
	
}
