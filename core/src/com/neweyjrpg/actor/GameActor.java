package com.neweyjrpg.actor;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.graphic.ActorAnimation;
import com.neweyjrpg.interfaces.IHandlesInputs;
import com.neweyjrpg.models.DirectionalInput;
import com.neweyjrpg.util.Conversion;

public class GameActor extends Actor implements IHandlesInputs {
	
	protected final static float ACTION_SPEED = 0.01f;
	
	//Fields
	private ActorAnimation animation;
	public ActorAnimation getAnimation() { return animation; }
	public void setAnimation(ActorAnimation animation) {	this.animation = animation;	}
	
	private Dir dir;
	public Dir getDir() {	return dir;	}
	public void setDir(Dir dir) {	this.dir = dir;	}
	
	protected float movespeed;
	public float getMovespeed() { return movespeed;	}
	public void setMovespeed(float movespeed) {	this.movespeed = movespeed;	}
	
	private boolean isMoving;
	
	
	private Body body;
	public Body getBody() { return this.body; }
	
	//Constructors
	public GameActor(World world, Texture charaSet, int pos, float x, float y){
		this.animation = new ActorAnimation(charaSet, pos);
		float width = this.animation.getAnim(Dir.UP).getKeyFrame(0).getRegionWidth(); 
		float height = this.animation.getAnim(Dir.UP).getKeyFrame(0).getRegionHeight();
		
		this.setPosition(x, y);
		this.setBounds(x, y, width, height);
		this.dir = Dir.DOWN;
		this.isMoving = false;
		movespeed = 2f;
		
		//Box2d settings
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(Conversion.toBox2d(x), Conversion.toBox2d(y));
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape square = new PolygonShape();
		square.setAsBox(Conversion.toBox2d(width)/2.0f, Conversion.toBox2d(height)/2.0f);
		fixtureDef.shape = square;
		fixtureDef.density = 1.0f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.friction = 1.0f;
		
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);

		square.dispose();
	}
	
	//Methods
	@Override
	public void draw(Batch batch, float deltaTime) {
		if (!this.isMoving)
			deltaTime = Constants.IDLE_FRAME * Constants.FRAME_DURATION; 
		batch.draw(this.animation.getFrame(deltaTime, this.dir, this.isMoving), this.getX(), this.getY());
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	public float[] getPosition() {
		return new float[]{getX(), getY()};
	}
	
	public void move(float x, float y){
		if (x == 0 && y == 0) {
			isMoving = false;
		}
		else {
			isMoving = true;
		}
		
		if (x<0) 
			this.dir=Dir.LEFT;
		else if (x>0) 
			this.dir=Dir.RIGHT;
		else
			this.body.setLinearVelocity(x, this.body.getLinearVelocity().y);
		
		if (y<0) 
			this.dir=Dir.DOWN;
		else if (y>0) 
			this.dir=Dir.UP;
		else
			this.body.setLinearVelocity(this.body.getLinearVelocity().x, y);
		
		this.body.applyLinearImpulse(x, y, getX(), getY(), false);
		
//		
//		MoveToAction action = new MoveToAction();
//		action.setPosition(getX() + x, getY() + y);
//		action.setDuration(ACTION_SPEED);
//		this.addAction(action);
//		this.act(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void moveFromInput(DirectionalInput input) {
		float tx=0f, ty=0f;
		Stack<Dir> dirs = input.getInputs();
		
		while (!dirs.isEmpty()) {
			Dir d = dirs.pop();
			switch (d){
			case UP:
				ty += movespeed;
				break;
			case RIGHT:
				tx += movespeed;
				break;
			case DOWN:
				ty -= movespeed;
				break;
			case LEFT:
				tx -= movespeed;
				break;
			}
		}
		
		move(tx, ty);
	}
	
}
