package com.neweyjrpg.actor.effects;

import com.neweyjrpg.actor.PlayerActor;

public class PlayerAttack extends AttackEffect {

	PlayerActor target;
	@Override
	public PlayerActor getTarget() { return this.target; }
	
	public PlayerAttack(PlayerActor target, float speed) {
		super(target, speed);
		this.target = target;
		this.offset.y += 8f;
	}
		
	@Override
	public void act(float delta) {
		if (this.duration <= 0) {
			this.target.setAttacking(false);
		}
		super.act(delta);
	}

}
