package com.neweyjrpg.actor.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.collider.OpenCollider;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.graphic.AttackAnimation;
import com.neweyjrpg.physics.BlockBody;

public class AttackEffect extends EffectActor {

	protected CharacterActor target;
	public GameActor getTarget() { return this.target; } 
	
	public AttackEffect(CharacterActor target, float speed) {
		super(target.getX(), target.getY(), 
				new BlockBody(Enums.PhysicalState.Open, new Rectangle(target.getX(), target.getY(), 1, 16)), 
				Enums.Priority.Above, speed);
		
		this.target = target;
		this.setCollider(new OpenCollider());
		this.setName("ATTACK_EFFECT");
		this.setAnimation(new AttackAnimation(speed));
		this.setDir(target.getDir());
	}
	
	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
		this.setX(this.getTarget().getX());
		this.setY(this.getTarget().getY());
		super.draw(batch, deltaTime, this.getX() + x, this.getY() + y);
	}
}
