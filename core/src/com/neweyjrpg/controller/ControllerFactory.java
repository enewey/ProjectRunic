package com.neweyjrpg.controller;

public class ControllerFactory {

	private static ControllerFactory factory;
	private static BadAIController bad;
	
	
	private ControllerFactory() {
		
	}
	
	public static ControllerFactory getInstance() {
		if (factory == null)
			factory = new ControllerFactory();
		
		return factory;
	}
	
	public BadAIController getBadAI() {
		if (bad == null)
			bad = new BadAIController();
		
		return bad;
	}
}
