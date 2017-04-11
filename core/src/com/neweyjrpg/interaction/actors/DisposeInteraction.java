package com.neweyjrpg.interaction.actors;

import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.ActorManager;
import com.neweyjrpg.manager.Manager;

public class DisposeInteraction extends ActorInteraction {

	public DisposeInteraction(IHandlesInteraction scene, String target) {
		super(scene, target);
	}

	@Override
	public Interaction process(Manager m) {
		((ActorManager)m).getActorByName(this.targetName).dispose();
		return this;
	}
	
	@Override
	public Object getData() {
		return null;
	}

}
