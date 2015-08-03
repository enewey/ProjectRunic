package com.neweyjrpg.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsModel {

	private BodyType type;
	public BodyType getType() {
		return type;
	}

	private Rectangle bounds;
	
	public Rectangle getBounds() {
		return bounds;
	}

	public PhysicsModel(BodyType type, Rectangle bounds) {
		this.type = type;
		this.bounds = bounds;
	}
}
