package com.neweyjrpg.actor.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IHasGraphics;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.physics.BlockBody;

public class CharacterActor extends GameActor implements IHasGraphics {

	// Fields
	private ActorAnimation animation;
	public ActorAnimation getAnimation() { return animation; }
	public void setAnimation(ActorAnimation animation) { this.animation = animation; }

	private IProducesInputs controller;
	public IProducesInputs getController() { return this.controller; }
	public void setController(IProducesInputs controller) { this.controller = controller; }

	// The direction this character is facing
	protected Dir dir;
	public Dir getDir() { return dir; }
	public void setDir(Dir dir) { this.dir = dir; }
	protected boolean directionFixed; 

	// Distance that a single move call will move this actor, in pixels.
	protected float movespeed;
	public float getMovespeed() { return movespeed;	}
	public void setMovespeed(float movespeed) { this.movespeed = movespeed;	}

	// Constructors
	public CharacterActor(Texture charaSet, int pos, float x, float y, BlockBody phys, Enums.Priority priority) {
		super(x, y, phys, priority);
		this.animation = new ActorAnimation(charaSet, pos);

		this.dir = Dir.DOWN;
		this.isMoving = false;
		this.movespeed = 2f;
		this.actionSpeed = Constants.DEFAULT_ACTION_SPEED;

		this.physPaddingX = (Constants.CHARA_WIDTH / 4f);
		this.physPaddingY = (Constants.CHARA_HEIGHT / 16f);
	}

	@Override
	public void draw(Batch batch, float deltaTime, float offsetX, float offsetY) {
		if (!isMoving || this.isBlocked)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION;
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), this.getX()+offsetX, this.getY()+offsetY);
	}

	@Override
	public void act(float delta) {
		//Adjust the direction Actor is facing before acting.
		if (!this.hasActions() && !this.actionQueue.isEmpty() && this.getMoveActionFromQueue() != null && !directionFixed) {
			this.adjustFacing(this.getMoveActionFromQueue().getAmountX(), this.getMoveActionFromQueue().getAmountY());
		}
		
		super.act(delta);
	}

	@Override
	public void move(float x, float y) {
		Vector2 v = this.adjustMovement(x, y);
		super.move(v.x, v.y);
	}
	
	@Override
	public void moveDistance(float x, float y, float speedScalar) {
		Vector2 v = this.adjustMovement(x, y);
		super.moveDistance(v.x, v.y, speedScalar);
	}
	
	private Vector2 adjustMovement(float x, float y) {
		x *= this.movespeed;
		y *= this.movespeed;
		return new Vector2(x, y);
	}
	
	private void adjustFacing(float x, float y) {
		if (x < 0)
			this.dir = Dir.LEFT;
		else if (x > 0)
			this.dir = Dir.RIGHT;

		if (y < 0)
			this.dir = Dir.DOWN;
		else if (y > 0)
			this.dir = Dir.UP;

		if (x == 0 && y == 0)
			this.isMoving = false;
		else
			this.isMoving = true;
	}

	public void dispose() {
		this.animation.dispose();
		super.dispose();
	}
	
	@Override
	public Vector2 getGraphicSize() {
		return new Vector2(this.animation.getFrame(0, Dir.UP, true).getRegionWidth(),
						   this.animation.getFrame(0, Dir.UP, true).getRegionHeight());
		
	}
}
