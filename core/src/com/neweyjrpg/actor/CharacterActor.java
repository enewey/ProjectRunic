package com.neweyjrpg.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.PhysicsModel;

public class CharacterActor extends GameActor {

	// Fields
	private ActorAnimation animation;

	public ActorAnimation getAnimation() {
		return animation;
	}

	public void setAnimation(ActorAnimation animation) {
		this.animation = animation;
	}

	private IProducesInputs controller;

	public IProducesInputs getController() {
		return this.controller;
	}

	public void setController(IProducesInputs controller) {
		this.controller = controller;
	}

	// The direction this character is facing
	private Dir dir;

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	// Distance that a single move call will move this actor, in pixels.
	protected float movespeed;

	public float getMovespeed() {
		return movespeed;
	}

	public void setMovespeed(float movespeed) {
		this.movespeed = movespeed;
	}

	protected boolean isMoving; // used for determining state of animation.
	protected float actionSpeed;

	public float getActionSpeed() {
		return actionSpeed;
	}

	public void setActionSpeed(float actionSpeed) {
		this.actionSpeed = actionSpeed;
	}

	// Constructors
	public CharacterActor(Texture charaSet, int pos, float x, float y, PhysicsModel phys, Enums.Priority priority) {
		super(x, y, phys, priority);
		this.animation = new ActorAnimation(charaSet, pos);

		this.dir = Dir.DOWN;
		this.isMoving = false;
		this.movespeed = 2f;
		this.actionSpeed = 0.01f;

		this.physPaddingX = (Constants.CHARA_WIDTH / 4f);
		this.physPaddingY = (Constants.CHARA_HEIGHT / 16f);
	}

	// Methods
	@Override
	public void draw(Batch batch, float deltaTime) {
		super.draw(batch, deltaTime);
		if (!isMoving)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION;
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), this.getX(), this.getY());
	}

	public void draw(Batch batch, float deltaTime, float x, float y) {
		super.draw(batch, deltaTime, x, y);
		if (!isMoving)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION;
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), x, y);
	}

	@Override
	public void act(float delta) {

		this.isMoving = false;
		if (this.hasActions()) {
			if (this.getActions().get(0) instanceof MoveToAction || this.getActions().get(0) instanceof MoveByAction) {
				this.isMoving = true;
			}
		}

		super.act(delta);
	}

	@Override
	public void move(float x, float y) {
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

		this.addAction(Actions.moveBy(x, y, this.actionSpeed));
	}

	public Vector2 getSpriteSize() {
		return new Vector2(this.animation.getFrame(0, getDir(), false).getRegionWidth(),
				this.animation.getFrame(0, getDir(), false).getRegionHeight());
	}

	public void dispose() {
		this.animation.dispose();
	}
}
