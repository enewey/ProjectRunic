package com.neweyjrpg.enums;

public class Enums {
	
	public enum Dir { UP, RIGHT, DOWN, LEFT }
	public enum PhysicalState { 
		StaticBlock,		//Never moves, blocks all objects.
		MovingBlock,		//Can be moving, blocks all objects.
		StaticPushable,		//Never moves of its own accord, can be pushed by moving objects.
		MovingPushable,		//Can be moving, can be pushed by moving objects.
		Open,				//Does not block, nor can be pushed.
		Custom				//Specifies the object should handle the collision according to its own rules.
	}
	
}
