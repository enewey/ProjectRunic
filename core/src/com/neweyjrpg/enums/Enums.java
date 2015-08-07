package com.neweyjrpg.enums;

public class Enums {
	
	public enum Dir { UP, RIGHT, DOWN, LEFT }
	public enum PhysicalState { 
		Static,		//Non-moving, blocking
		Blocking,	//May be moving, blocking
		Pushable, 	//Moving or non-moving, can be pushed by blocking/elastic types
		Elastic, 	//Blocking/pushable types bounce off
		Light,		//Does not
		Gaseous
	}
	
}
