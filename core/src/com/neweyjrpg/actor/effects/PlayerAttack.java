package com.neweyjrpg.actor.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.actor.PlayerActor;

public class PlayerAttack extends AttackEffect {

	PlayerActor target;
	@Override
	public PlayerActor getTarget() { return this.target; }
	
	public PlayerAttack(PlayerActor target, float speed) {
		super(target, speed);
		this.target = target;
	}
		
	@Override
	public void act(float delta) {
		this.duration -= delta;
		if (this.duration <= 0) {
			this.target.setAttacking(false);
			this.dispose();
		}
		//super.act(delta);
	}

}
