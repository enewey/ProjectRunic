package com.neweyjrpg.collider;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IAttackable;

public class AttackCollider extends LineCollider {

	@Override
	public boolean checkCollision(GameActor actor, GameActor subject) {
		if (actor.getPhysicsBody().overlaps(subject.getPhysicsBody())) {
			if (subject instanceof IAttackable) {
				((IAttackable) subject).takeAttack();
			}
			return true;
		}
		return false;
	}
}
