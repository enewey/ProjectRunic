package com.neweyjrpg.collider;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IHandlesCollision;

public class BlockingCollider implements IHandlesCollision<GameActor>{

	@Override
	public void handleCollision(GameActor actor, GameActor subject) {
		float currX, currY, oldX, oldY, subX, subY;
		if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())){
			
			switch (actor.getPhysicsModel().getType()) {
			default:		
				oldX = actor.getOldPosition().x;
				oldY = actor.getOldPosition().y;
				currX = actor.getPhysicsModel().getBounds().x;
				currY = actor.getPhysicsModel().getBounds().y;
				subX = subject.getPhysicsModel().getBounds().x;
				subY = subject.getPhysicsModel().getBounds().y;
				
				if (Math.abs(subX - currX) > Math.abs(subY - currY)) {
					if (interpolateY(actor, subject, currX, currY)) break;
					if (interpolateX(actor, subject, currX, currY)) break;
				} else {
					if (interpolateX(actor, subject, currX, currY)) break;
					if (interpolateY(actor, subject, currX, currY)) break;
				}
				
				if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())) {
					actor.setPhysicalPosition(oldX, oldY);
					Vector2 moveCheck = actor.getPhysicsModel().moveOff(subject.getPhysicsModel());
					if (moveCheck != null) {
						actor.movePhysicalPosition(moveCheck.x, moveCheck.y);
					}
				}
				break;
			}				
		}
	}
	
	private boolean interpolateY(GameActor actor, GameActor subject, float currX, float currY) {		
		float oldY = actor.getOldPosition().y;
		float interpY = currY;
		
		while (Math.abs(interpY - oldY) > 0.01f){
			if (!actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())){
				return true;
			}
			interpY = (interpY+oldY) / 2f;
			actor.setPhysicalPosition(currX, interpY);
			
		}
		actor.setPhysicalPosition(currX, oldY);
		return !actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds());
	}
	
	private boolean interpolateX(GameActor actor, GameActor subject, float currX, float currY) {
		float oldX = actor.getOldPosition().x;
		float interpX = currX;
		
		while (Math.abs(interpX - oldX) > 0.01f){
			if (!actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())) {
				return false;
			}
			interpX = (interpX+oldX) / 2f;
			actor.setPhysicalPosition(interpX, currY);
		}
		actor.setPhysicalPosition(oldX, currY);
		return !actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds());
	}

}
