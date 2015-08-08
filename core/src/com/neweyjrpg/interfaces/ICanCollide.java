package com.neweyjrpg.interfaces;

public interface ICanCollide<T> {
	public void collideInto(T obj);
	public void collisionFrom(T obj);
}
