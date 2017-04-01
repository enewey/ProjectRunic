package com.neweyjrpg.interaction;

import com.badlogic.gdx.utils.Array;
import com.neweyjrpg.interfaces.Interaction;
import com.neweyjrpg.manager.Manager;

public class MessageInteraction implements Interaction {
	
	private Array<String> message;
	public Array<String> getData() { return message; }
	
	public MessageInteraction(String str) { 
		this.message = new Array<String>(true, str.length()/156, String.class);
		while (str.length() > 156) { 
			this.message.add(formatString(str));
			str = str.substring(156);
		}
		this.message.add(formatString(str));
	}
	
	private String formatString(String str) { 
		if (str.length() > 156)
			return str.substring(0, 39) + "\n" + str.substring(39, 78) + "\n" + str.substring(78, 117) + "\n" + str.substring(117, 156);
		else if (str.length() > 117)
			return str.substring(0, 39) + "\n" + str.substring(39, 78) + "\n" + str.substring(78, 117) + "\n" + str.substring(117, str.length());
		else if (str.length() > 78)
			return str.substring(0, 39) + "\n" + str.substring(39, 78) + "\n" + str.substring(78, str.length());
		else if (str.length() > 39)
			return str.substring(0, 39) + "\n" + str.substring(39, str.length());
		else
			return str;
	}
	
	public Interaction process(Manager m) {
		return this;
	}
}
