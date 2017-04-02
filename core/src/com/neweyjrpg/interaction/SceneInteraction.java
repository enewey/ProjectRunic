package com.neweyjrpg.interaction;

import com.badlogic.gdx.Gdx;
import com.neweyjrpg.enums.Enums.SceneAction;
import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.interfaces.IDrawsGraphics;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.map.GameMap;

public class SceneInteraction extends Interaction {

	private SceneAction action;
	private float duration; //GameScene doesn't have an act model.. so we use delta time here.
	private Object[] args;
	private boolean isBlocking;
	public boolean isBlocking() { return this.isBlocking; }
	
	private float elapsed;
	
	public SceneInteraction(GameScene scene, SceneAction action, float duration, boolean isBlocking, Object ...args) {
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
		this.elapsed += Gdx.graphics.getDeltaTime();
		
		switch(action) {
		case ChangeColor:
			if (IDrawsGraphics.class.isAssignableFrom(m.getClass())) {
				IDrawsGraphics g = (IDrawsGraphics)m;
				g.massColorAdd(0, 0, 0, (Float)args[0] * (elapsed / duration > 1 ? 1 : elapsed / duration));
			}
			break;
		default:
			break;
		}
		
		return this;
	}
	
	public void processMap(GameMap m) {
		switch(action) {
		case ChangeColor:
			if (IDrawsGraphics.class.isAssignableFrom(m.getClass())) {
				IDrawsGraphics g = (IDrawsGraphics)m;
				g.massColorAdd(0, 0, 0, (Float)args[0] * (elapsed / duration > 1 ? 1 : elapsed / duration));
			}
			break;
		default:
			break;
		}
	}
}
