package com.neweyjrpg.interaction.actors;

import com.neweyjrpg.actor.characters.CharacterActor;
import com.neweyjrpg.actor.characters.PlayerActor;
import com.neweyjrpg.actor.effects.AttackEffect;
import com.neweyjrpg.actor.effects.EffectActor;
import com.neweyjrpg.actor.effects.PlayerAttack;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.ActorManager;

public class AttackInteraction extends ActorInteraction {
	
	EffectActor effect;
	
	public AttackInteraction(IHandlesInteraction scene, String target) {
		super(scene, target);
	}
	
	public Interaction process(ActorManager m) {
		if (this.started) {
			return super.process(m);
		}
		
		this.target = m.getActorByName(this.targetName);
		if (this.target instanceof PlayerActor) {
			this.effect = new PlayerAttack((PlayerActor)this.target, 3.5f);
		} else {
			this.effect = new AttackEffect((CharacterActor)this.target, 3.5f);
		}
		
		m.addActor(this.effect);
		return super.process(m);
	}

	@Override
	public Boolean getData() {
		return true;
	}

}
