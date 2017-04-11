package com.neweyjrpg.actor.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.interfaces.IAttackable;
import com.neweyjrpg.interfaces.IAttacker;
import com.neweyjrpg.physics.BlockBody;
import com.neweyjrpg.util.Funcs;

public class NPCActor extends CharacterActor implements IAttackable {
	
	public float iframes = 0.1f;
	
	public NPCActor(Texture charaSet, int pos, float x, float y, BlockBody phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
	}
	
	@Override
	public void act(float delta) {
		if (!this.hasActions()) {

		}
		super.act(delta);
	}
	
	@Override
	public Color getColor() {
		if (this.getState().get("invulnerable").isActive()) {
			if (this.getState().get("invulnerable").getData() % 0.005 > 0.0025) {
				Color c = super.getColor().cpy();
				return c.set(c.r, c.g, c.b, Constants.INVULNERABILITY_ALPHA);
			}
		}
		return super.getColor();
	}

	@Override
	public void takeAttack(IAttacker attacker) {
		if (!this.getState().get("invulnerable").isActive()) {
			System.out.println(this.getName() + " Attacked " + this.phys.getCenter().x+" "+this.phys.getCenter().y);
			this.getState().get("invulnerable").activate(this.iframes);
			Vector2 m = Funcs.getMoveAwayFrom(this.getPhysicsBody().getCenter(), attacker.getAttackSource());
			this.forceMove(this.getDistanceSpeedMove(m.x, m.y, 32, 3));
		}	
	}
}
