package com.neweyjrpg.interaction.actors;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.Manager;

public abstract class ActorInteraction extends Interaction {
	
	protected String targetName; //name of actor this movement interaction should target
	
	protected GameActor target;
	public GameActor getTarget() { return this.target; }
	
	public ActorInteraction(IHandlesInteraction scene, String target) {
		super(scene);
		this.targetName = target;
	}
	
	public Interaction process(Manager m) {
		return super.process(m);
	}
}
