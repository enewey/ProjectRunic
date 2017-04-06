package com.neweyjrpg.collider;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IHandlesCollision;

public class OpenCollider implements IHandlesCollision<GameActor> {

	@Override
	public boolean handleCollision(GameActor obj, GameActor obj2) {
		return false;
	}

}
