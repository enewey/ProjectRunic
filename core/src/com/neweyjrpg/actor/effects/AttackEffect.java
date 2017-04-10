package com.neweyjrpg.actor.effects;

import java.util.LinkedList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.CharacterActor;
import com.neweyjrpg.collider.AttackCollider;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.PhysicalState;
import com.neweyjrpg.graphic.AttackAnimation;
import com.neweyjrpg.physics.BlockBody;
import com.neweyjrpg.physics.LineBody;
import com.neweyjrpg.util.Line;

public class AttackEffect extends EffectActor {

	protected CharacterActor target;
	public CharacterActor getTarget() { return this.target; }
	
	protected Vector2 offset;
	protected LinkedList<Line> hitboxFrames;
	
	protected float frameCounter;
	private TextureRegion hitboxDebug;
	
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
		
		this.frameCounter = frameDuration;
		this.hitboxFrames = new LinkedList<Line>();
		
		
		Vector2[] vecs = {
			new Vector2(0f, -24f),
			new Vector2(-12, -20.78f),
			new Vector2(-20.78f, -12f),
			new Vector2(-24f, 0f)
		};
		
		float degs = 0f;
		switch (this.getDir()) {
		case UP:
			degs = 270;
			break;
		case RIGHT:
			degs = 180;
			break;
		case DOWN:
			degs = 90;
			break;
		default:
			break;
		}
		
		Vector2 anchor = new Vector2(target.getX(), target.getY());
		for (Vector2 v : vecs) {
			Line i = new Line(anchor, v.cpy().add(anchor)).rotateTail(degs, false);
			this.hitboxFrames.addLast(i);
		}
		
		this.phys = new LineBody(PhysicalState.Custom, this.hitboxFrames.getFirst());
		Vector2 center = this.getTarget().getPhysicsBody().getCenter();
		this.phys.setPosition(center.x, center.y);
		
		this.setTexture();
	}
	
	private void setTexture() {
		if (this.phys != null) {			
			this.hitboxDebug = ((LineBody)this.phys).getDebugTexture();
		}
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
		
		//Debugging
		Vector2 v = ((LineBody)this.phys).getLine().getBottomLeft();
		batch.draw(this.hitboxDebug, v.x + x, v.y + y);
	}
	
	
	@Override
	public void act(float delta) {
		if (this.frameCounter <  this.duration % frameDuration) {
			this.advanceHitbox();
		}
		this.frameCounter = this.duration % frameDuration;
		super.act(delta);
	}
	
	private void advanceHitbox() {
		if (!this.hitboxFrames.isEmpty()) {
			Line seg = this.hitboxFrames.removeFirst();
			((LineBody)this.phys).setLine(seg);
			Vector2 center = this.getTarget().getPhysicsBody().getCenter();
			this.phys.setPosition(center.x, center.y);
			this.setTexture();
		}
	}
}
