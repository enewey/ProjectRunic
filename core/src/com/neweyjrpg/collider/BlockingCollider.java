package com.neweyjrpg.collider;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.interfaces.IHandlesCollision;

public class BlockingCollider implements IHandlesCollision<GameActor>{

	@Override
	public void handleCollision(GameActor actor, GameActor subject) {
		float currX = actor.getPhysicsModel().getBounds().x;
		float currY = actor.getPhysicsModel().getBounds().y;
		float oldX = actor.getOldPosition().x;
		float oldY = actor.getOldPosition().y;
		float interpX = currX;
		float interpY = currY;
		if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())){
			//TODO check body types here
			
			switch (actor.getPhysicsModel().getType()) {
			case StaticBody:
				while (subject.getPhysicsModel().getBounds().overlaps(actor.getPhysicsModel().getBounds())
						&& Math.abs(interpY - oldY) > 0.01f){
					interpY = (interpY+oldY) / 1.05f;
					subject.setPhysicalPosition(currX, interpY);
				}
				while (subject.getPhysicsModel().getBounds().overlaps(actor.getPhysicsModel().getBounds())
						&& Math.abs(interpX - oldX) > 0.01f){
					interpX = (interpX+oldX) / 1.05f;
					subject.setPhysicalPosition(interpX, currY);
				}
				if (subject.getPhysicsModel().getBounds().overlaps(actor.getPhysicsModel().getBounds())) {
					subject.setPhysicalPosition(oldX, oldY);
					Vector2 moveCheck = subject.getPhysicsModel().moveOff(actor.getPhysicsModel());
					if (moveCheck != null) {
						subject.movePhysicalPosition(moveCheck.x, moveCheck.y);
					}
				}
				break;
			case KinematicBody:
				break;
			default:
				while (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())
						&& Math.abs(interpY - oldY) > 0.01f){
					interpY = (interpY+oldY) / 2;
					actor.setPhysicalPosition(currX, interpY);
				}
				while (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())
						&& Math.abs(interpX - oldX) > 0.01f){
					interpX = (interpX+oldX) / 2;
					actor.setPhysicalPosition(interpX, currY);
				}
				if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())) {
					actor.setPhysicalPosition(oldX, oldY);
					Vector2 moveCheck = actor.getPhysicsModel().moveOff(subject.getPhysicsModel());
					if (moveCheck != null) {
						actor.movePhysicalPosition(moveCheck.x, moveCheck.y);
					}
				}
				
				
				actor.setPhysicalPosition(currX, oldY);
				if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())) {
					actor.setPhysicalPosition(oldX, currY);
					if (actor.getPhysicsModel().getBounds().overlaps(subject.getPhysicsModel().getBounds())) {
						actor.setPhysicalPosition(oldX, oldY);
						Vector2 moveCheck = actor.getPhysicsModel().moveOff(subject.getPhysicsModel());
						if (moveCheck != null) {
							actor.movePhysicalPosition(moveCheck.x, moveCheck.y);
						}
					}
				}
				break;
			}				
		}
	}

}
