package com.neweyjrpg.collider;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IHandlesCollision;

public class BlockingCollider implements IHandlesCollision<GameActor>{

	@Override
	public boolean checkCollision(GameActor actor, GameActor subject) {
		boolean ret = false;
		if (actor.getPhysicsBody().overlaps(subject.getPhysicsBody())){
			float currX, currY, oldX, oldY, subX, subY;
			oldX = actor.getOldPosition().x;
			oldY = actor.getOldPosition().y;
			currX = actor.getPhysicsBody().getX();
			currY = actor.getPhysicsBody().getY();
			subX = subject.getPhysicsBody().getX();
			subY = subject.getPhysicsBody().getY();
			
			ret = true;
			
			switch (subject.getPhysicsBody().getType()) {
			case Open:
				break;
			case Custom:
				subject.collideInto(actor); //TODO: this should work, right?
				break;
			//Pushable behavior	
			case MovingPushable:
			case StaticPushable:
				float moveX = currX - oldX;
				float moveY = currY - oldY;
				actor.setPhysicalPosition(oldX, oldY);
				
				Vector2 ac = actor.getPhysicsBody().getCenter();
				Vector2 sc = subject.getPhysicsBody().getCenter();
				float diffX = sc.x - ac.x;
				float diffY = sc.y - ac.y;
				if (Math.abs(diffX) > Math.abs(diffY)) 
					moveY = 0;
				else 
					moveX = 0;
				
				if (actor.isMoving())
					subject.forceMove(Actions.moveBy(moveX, moveY, 0f));
				break;
				
			default:
				if (Math.abs(subX - currX) > Math.abs(subY - currY)) {
					if (interpolateY(actor, subject, currX, currY)) break;
					if (interpolateX(actor, subject, currX, currY)) break;
				} else {
					if (interpolateX(actor, subject, currX, currY)) break;
					if (interpolateY(actor, subject, currX, currY)) break;
				}
				
				if (actor.getPhysicsBody().overlaps(subject.getPhysicsBody())) {
					actor.setPhysicalPosition(oldX, oldY);
					Vector2 moveCheck = actor.getPhysicsBody().moveOff(subject.getPhysicsBody());
					if (moveCheck != null) {
						actor.movePhysicalPosition(moveCheck.x, moveCheck.y);
					}
				}
				break;
			}
		}
		
		return ret;
	}
	
	private boolean interpolateY(GameActor actor, GameActor subject, float currX, float currY) {		
		float oldY = actor.getOldPosition().y;
		float interpY = currY;
		
		while (Math.abs(interpY - oldY) > 0.01f){
			if (!actor.getPhysicsBody().overlaps(subject.getPhysicsBody())){
				return true;
			}
			interpY = (interpY+oldY) / 2f;
			actor.setPhysicalPosition(currX, interpY);
			
		}
		actor.setPhysicalPosition(currX, oldY);
		return !actor.getPhysicsBody().overlaps(subject.getPhysicsBody());
	}
	
	private boolean interpolateX(GameActor actor, GameActor subject, float currX, float currY) {
		float oldX = actor.getOldPosition().x;
		float interpX = currX;
		
		while (Math.abs(interpX - oldX) > 0.01f){
			if (!actor.getPhysicsBody().overlaps(subject.getPhysicsBody())) {
				return true;
			}
			interpX = (interpX+oldX) / 2f;
			actor.setPhysicalPosition(interpX, currY);
		}
		actor.setPhysicalPosition(oldX, currY);
		return !actor.getPhysicsBody().overlaps(subject.getPhysicsBody());
	}

}
