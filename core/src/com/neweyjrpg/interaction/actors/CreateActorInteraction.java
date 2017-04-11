package com.neweyjrpg.interaction.actors;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.ActorManager;
import com.neweyjrpg.manager.Manager;

public class CreateActorInteraction extends Interaction {

	private GameActor add;
	
	public CreateActorInteraction(IHandlesInteraction scene, GameActor add) {
		super(scene);
		this.add = add;
	}

	@Override
	public Interaction process(Manager m) {
		((ActorManager)m).addActor(add);
		return super.process(m);
	}
	
	@Override
	public Object getData() {
		return null;
	}

}
