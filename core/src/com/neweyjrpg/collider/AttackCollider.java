package com.neweyjrpg.collider;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IAttackable;
import com.neweyjrpg.interfaces.IAttacker;

public class AttackCollider extends LineCollider {
	
	private IAttacker attacker;
	
	public AttackCollider(IAttacker attacker) {
		this.attacker = attacker;
	}

	@Override
	public boolean checkCollision(GameActor actor, GameActor subject) {
		if (subject == (GameActor)attacker) {
			return false;
		}
		if (actor.getPhysicsBody().overlaps(subject.getPhysicsBody())) {
			if (subject instanceof IAttackable) {
				((IAttackable) subject).takeAttack(attacker);
			}
			return true;
		}
		return false;
	}
}
