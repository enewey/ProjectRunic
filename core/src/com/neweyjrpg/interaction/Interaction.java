package com.neweyjrpg.interaction;

import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.manager.Manager;

public abstract class Interaction {
	private GameScene scene;
	protected boolean started;
	protected boolean completed;
	public Interaction(GameScene scene) {
		this.scene = scene;
		this.init();
	} 
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
		this.scene.onInteractionComplete(this);
	}
	
}
