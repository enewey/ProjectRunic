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
	
	/**
	 * Collision body types for tiles
	 */
	public enum TileBody {
		Open,
		Block,
		Circle
	}
	public static TileBody tileBody(String in) {
		String str = in.toUpperCase();
		if (str.equals("OPEN")) {
			return TileBody.Open;
		} else if (str.equals("BLOCK")) {
			return TileBody.Block;
		} else if (str.equals("CIRCLE")) {
			return TileBody.Circle;
		}
		
		//default?
		return TileBody.Open;
	}
	
	/**
	 * Draw priority for tiles in relation to actors
	 */
	public enum Priority {
		Below,
		Same,
		Above
	}
	public static Priority priority(String in) {
		String str = in.toUpperCase();
		if (str.equals("BELOW")) {
			return Priority.Below;
		} else if (str.equals("SAME")) {
			return Priority.Same;
		} else if (str.equals("ABOVE")) {
			return Priority.Above;
		}
		
		//default?
		return Priority.Below;
	}
	
	public enum Move {
		StepDir, //a step in a cardinal direction
		StepToVec, // step towards an X,Y coordinate
		Face, // turn actor in a specific direction with no spatial movement
		Pause // pause for a tick
	}
}
