package com.neweyjrpg.collider;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IHandlesCollision;
import com.neweyjrpg.util.Line;

public abstract class LineCollider implements IHandlesCollision<GameActor> {

	protected Line line;
}
