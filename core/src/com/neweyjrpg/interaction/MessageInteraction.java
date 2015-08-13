package com.neweyjrpg.interaction;

import com.neweyjrpg.interfaces.Interaction;

public class MessageInteraction implements Interaction {
	
	private String message;
	public String getData() { return message; }
	
	public MessageInteraction(String message) { 
		this.message = message;
	}
}
