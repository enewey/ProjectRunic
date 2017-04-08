package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.interfaces.IAttackable;
import com.neweyjrpg.models.ActorState;
import com.neweyjrpg.physics.BlockBody;

public class NPCActor extends CharacterActor implements IAttackable {
	
	public float iframes = 4f;
	
	public NPCActor(Texture charaSet, int pos, float x, float y, BlockBody phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
		this.getState().put("invulnerable", new ActorState());
	}
	
	@Override
	public void act(float delta) {
		if (!this.hasActions()) {

		}
		for (ActorState state : this.getState().values()) {
			state.tick(delta);
		}
		super.act(delta);
	}

	@Override
	public void takeAttack() {
		if (!this.getState().get("invulnerable").isActive()) {
			System.out.println(this.getName() + " Attacked");
			this.getState().get("invulnerable").activate(this.iframes);
		}
		
	}
}
