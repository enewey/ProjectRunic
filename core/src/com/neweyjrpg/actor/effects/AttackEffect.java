package com.neweyjrpg.actor.effects;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.collider.AttackCollider;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.graphic.AttackAnimation;
import com.neweyjrpg.physics.BlockBody;
import com.neweyjrpg.physics.LineBody;
import com.neweyjrpg.util.Line;

public class AttackEffect extends EffectActor {

	protected CharacterActor target;
	protected Vector2 offset;
	public GameActor getTarget() { return this.target; } 
	protected LinkedList<Vector2> hitboxFrames;
	
	public AttackEffect(CharacterActor target, float speed) {
		super(target.getX(), target.getY(), 
				new BlockBody(Enums.PhysicalState.Custom, new Rectangle(target.getX(), target.getY(), 1, 16)), 
				Enums.Priority.Above, speed);
		
		this.target = target;
		this.setCollider(new AttackCollider());
		this.setName("ATTACK_EFFECT");
		this.setAnimation(new AttackAnimation(speed));
		this.setDir(target.getDir());
		calcOffset();
		
		this.phys = new LineBody(PhysicalState.Open,
								 new Vector2(target.getX(), target.getY()),
								 new Vector2(target.getX(), target.getY() - 24f));
		
		this.hitboxFrames = new LinkedList<Vector2>();
		
		this.hitboxFrames.add(new Vector2(target.getX(), target.getY()));
		this.hitboxFrames.add(new Vector2(target.getX() - 12f, target.getY() - 20.78f));
		
		this.hitboxFrames.add(new Vector2(target.getX(), target.getY()));
		this.hitboxFrames.add(new Vector2(target.getX() - 20.78f, target.getY() - 12));
		
		this.hitboxFrames.add(new Vector2(target.getX(), target.getY()));
		this.hitboxFrames.add(new Vector2(target.getX() - 24f, target.getY()));
		
	}
	
	private void calcOffset() {
		float offx = (this.getGraphicSize().x - target.getGraphicSize().x) / 2.0f;
		float offy = (this.getGraphicSize().y - target.getGraphicSize().y) / 2.0f;
		this.offset = new Vector2(offx, offy);
	}
	
	@Override
	public void draw(Batch batch, float deltaTime, float x, float y) {
		this.setX(this.getTarget().getX() - this.offset.x);
		this.setY(this.getTarget().getY() - this.offset.y);
		super.draw(batch, deltaTime, this.getX() + x, this.getY() + y);
	}
	
	
	protected float frameCounter = Constants.FRAME_DURATION;
	@Override
	public void act(float delta) {
		if (this.frameCounter < this.duration % Constants.FRAME_DURATION) {
			this.advanceHitbox();
		}
		this.frameCounter = this.duration % Constants.FRAME_DURATION;
		super.act(delta);
	}
	
	private void advanceHitbox() {
		if (!this.hitboxFrames.isEmpty()) {
			((LineBody)this.phys).getLine().a = this.hitboxFrames.removeFirst();
			((LineBody)this.phys).getLine().b = this.hitboxFrames.removeFirst();
		}
	}
}
