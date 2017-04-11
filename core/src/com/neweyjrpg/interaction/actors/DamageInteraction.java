package com.neweyjrpg.interaction.actors;

import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IAttackable;
import com.neweyjrpg.interfaces.IAttacker;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.ActorManager;
import com.neweyjrpg.manager.Manager;

public class DamageInteraction extends ActorInteraction {

	IAttacker source;
	
	public DamageInteraction(IHandlesInteraction scene, String target, IAttacker source) {
		super(scene, target);
		this.source = source;
	}
	
	@Override
	public Interaction process(Manager m) {
		this.target = ((ActorManager)m).getActorByName(this.targetName);
		
		if (this.target instanceof IAttackable) {
			((IAttackable) this.target).takeAttack(source);
		}
		return super.process(m);
	}
	
	@Override
	public Boolean getData() {
		// TODO Auto-generated method stub
		return true;
	}

}
