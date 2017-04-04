package com.neweyjrpg.interaction.windows;

import com.badlogic.gdx.utils.Array;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.manager.WindowManager;
import com.neweyjrpg.window.MessageWindow;

public class MessageInteraction extends WindowInteraction {
	
	private Array<String> message;
	public Array<String> getData() { return message; }
	
	public MessageInteraction(GameScene scene, String str) {
		super(scene);
		this.message = new Array<String>(true, str.length()/156, String.class);
		while (str.length() > 156) { 
			this.message.add(formatString(str));
			str = str.substring(156);
		}
		this.message.add(formatString(str));
	}
	
	//TODO: Make string format dynamic based on Constants.GAME_WIDTH
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
		super.process(m);
		for (String s : this.getData()) {
			((WindowManager)m).addWindow(new MessageWindow(this, 0,0,Constants.GAME_WIDTH,80,s));
		}
		return this;
	}
}
