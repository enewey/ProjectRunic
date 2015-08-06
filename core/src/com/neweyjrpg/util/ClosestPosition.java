package com.neweyjrpg.util;

import java.util.Comparator;

import com.neweyjrpg.actor.GameActor;

public class ClosestPosition implements Comparator<GameActor> {

	private GameActor anchor;
	
	public ClosestPosition(GameActor anchor) {
		this.anchor = anchor;
	}
	
	private float dist(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2)));
	}
	
	@Override
	public int compare(GameActor a1, GameActor a2) {
		float dist1 = dist(a1.getPhysicsModel().getBounds().x, a1.getPhysicsModel().getBounds().y,
				anchor.getPhysicsModel().getBounds().x, anchor.getPhysicsModel().getBounds().y);
		float dist2 = dist(a2.getPhysicsModel().getBounds().x, a2.getPhysicsModel().getBounds().y,
				anchor.getPhysicsModel().getBounds().x, anchor.getPhysicsModel().getBounds().y);
		
		
		return (int)((dist1*1000f) - (dist2*1000f));
	}

}
