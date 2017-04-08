package com.neweyjrpg.interfaces;

public interface IHandlesCollision<T> {
	
	/**
	 *  Checks if collision occurs between two objects, and then handles the collision. 
	 * @param obj
	 * @param obj2
	 * @return - true if collision occurred.
	 */
	public boolean checkCollision(T obj, T obj2);
}
