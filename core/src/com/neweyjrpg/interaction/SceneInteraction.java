package com.neweyjrpg.interaction;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.enums.Enums.SceneAction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.interfaces.IManagesGraphics;
import com.neweyjrpg.manager.Manager;

public class SceneInteraction extends Interaction {

	private SceneAction action;
	private float duration; //GameScene doesn't have an act model.. so we use delta time here.
	private Object[] args;
	private boolean isBlocking;
	public boolean isBlocking() { return this.isBlocking; }
	
	private float elapsed;
	private float delta;
	
	public SceneInteraction(IHandlesInteraction scene, SceneAction action, float duration, boolean isBlocking, Object ...args) {
		super(scene);
		this.action = action;
		this.args = args;
		this.duration = duration;
		this.isBlocking = isBlocking;
		
		this.elapsed = 0f;
	}
	
	public void init() {
		super.init();
		this.elapsed = 0f;
	}
	
	@Override
	public Boolean getData() {
		return this.elapsed >= this.duration;
	}

	@Override
	public Interaction process(Manager m) {
		this.delta = Gdx.graphics.getDeltaTime();
		this.elapsed += this.delta;
		
		switch(action) {
		case ChangeColor:
			//include all graphics drawing cases here
			if (m instanceof IManagesGraphics) {
				IManagesGraphics g = (IManagesGraphics)m;
				return this.processDraw(g);
			}
			break;
		default:
			break;
		}
		
		return this;
	}
	
	private Interaction processDraw(IManagesGraphics g) {
		switch(action) {
		case ChangeColor:
			g.massColorLerp((Float)args[0], (Float)args[1], (Float)args[2], (Float)args[3], 
					this.delta / (this.duration - this.elapsed));
			break;
		default:
			break;
		}
		
		return this;
	}
}
