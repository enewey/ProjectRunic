package com.neweyjrpg.actor.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.enums.Enums.Priority;
import com.neweyjrpg.graphic.EffectAnimation;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.physics.BlockBody;

public class EffectActor extends GameActor {

	private EffectAnimation animation;
	public EffectAnimation getAnimation() { return animation; }
	public void setAnimation(EffectAnimation animation) { this.animation = animation; }
	
	protected float duration;
	protected float startTime;
	
	// The direction this character is facing
	private Dir dir;
	public Dir getDir() { return dir; }
	public void setDir(Dir dir) { this.dir = dir; }
	
	public EffectActor(float x, float y, BlockBody phys, Priority priority, float speed) {
		super(x, y, phys, priority);
		this.duration = 0.5f / speed;
		this.startTime = -1;
	}
	
	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
		if (startTime < 0) { this.startTime = deltaTime; }
		super.draw(batch, deltaTime);
		batch.draw(this.animation.getFrame(deltaTime - startTime, this.dir, true), x, y);
	}
	
	@Override
	public void act(float delta) {
		this.duration-=delta;
		if (this.duration <= 0) {
			this.dispose();
		}
	}

	@Override
	public Vector2 getSpriteSize() {
		//return new Vector2(this.animation.getTest().getRegionWidth(), this.animation.getTest().getRegionHeight());
		return new Vector2(this.animation.getFrame(0, getDir(), false).getRegionWidth(),
				this.animation.getFrame(0, getDir(), false).getRegionHeight());
	}

	@Override
	public void dispose() {
		this.animation.dispose();
		super.dispose();
	}

	@Override
	public IProducesInputs getController() {
		return null;
	}

}
