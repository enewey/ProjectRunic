package com.neweyjrpg.interfaces;

public interface ICanCollide<T> {
	public boolean collideInto(T obj);
	public boolean collisionFrom(T obj);
}
