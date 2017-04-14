package com.neweyjrpg.interaction.windows;

import java.util.ArrayList;

import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.manager.WindowManager;
import com.neweyjrpg.util.Funcs;
import com.neweyjrpg.window.MessageWindow;

public class MessageInteraction extends WindowInteraction {
	
	protected ArrayList<String> message;
	public ArrayList<String> getData() { return message; }
	protected String font;
	protected int x,y,width,height;
	
	public MessageInteraction(IHandlesInteraction scene, int x, int y, int width, int height, String font, String str) {
		super(scene);
		this.font = font;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.message = Funcs.formatString(str, this.font, this.width, Constants.WINDOW_PADDING);
	}
	
	public Interaction process(Manager m) {
		super.process(m);
		for (String s : this.getData()) {
			((WindowManager)m).addWindow(new MessageWindow(this, x,y,width,height,this.font,s));
		}
		return this;
	}
}
