package com.neweyjrpg.interaction;

import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.PlayerActor;
import com.neweyjrpg.actor.effects.AttackEffect;
import com.neweyjrpg.actor.effects.EffectActor;
import com.neweyjrpg.actor.effects.PlayerAttack;
import com.neweyjrpg.interfaces.InteractionCompleteListener;
import com.neweyjrpg.manager.ActorManager;

public class AttackInteraction extends ActorInteraction {
	
	EffectActor effect;
	
	public AttackInteraction(InteractionCompleteListener scene, String target) {
		super(scene, target);
	}
	
	public Interaction process(ActorManager m) {
		if (this.started) {
			return super.process(m);
		}
		
		this.target = m.getActorByName(this.targetName);
		if (this.target instanceof PlayerActor) {
			this.effect = new PlayerAttack((PlayerActor)this.target, 3f);
		} else {
			this.effect = new AttackEffect((CharacterActor)this.target, 3f);
		}
		
		m.addActor(this.effect);
		return super.process(m);
	}

	@Override
	public Boolean getData() {
		return true;
	}

}
