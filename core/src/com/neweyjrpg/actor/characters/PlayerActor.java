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

public class PlayerActor extends CharacterActor implements IAttacker, IAttackable {
	
	private float iframes = 1f;
	
	private boolean isAttacking;
	public boolean isAttacking() { return this.isAttacking; }
	public void setAttacking(boolean b) { this.isAttacking = b; }
	
	public PlayerActor(Texture charaSet, int pos, float x, float y, BlockBody phys, Enums.Priority priority) {
		super(charaSet, pos, x, y, phys, priority);
	}
	
	@Override
	public float getAttackPower() {
		return 2f;
	}
	
	@Override
	public Vector2 getAttackSource() {
		return this.getPhysicsBody().getCenter();
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
			System.out.println(this.getName() + " Attacked " + this.phys.getX() +" "+this.phys.getY());
			this.getState().get("invulnerable").activate(this.iframes);
			Vector2 m = Funcs.getMoveAwayFrom(this.getPhysicsBody().getCenter(), attacker.getAttackSource());
			this.addMove(this.getDistanceSpeedMove(m.x, m.y, 32, 3));
		}
	}
}
