package com.neweyjrpg.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface IAttacker {

	public float getAttackPower();
	public Vector2 getAttackSource();
	
	
	public boolean isAttacking();
	public void setAttacking(boolean b);
}
