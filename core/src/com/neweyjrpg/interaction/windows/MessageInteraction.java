package com.neweyjrpg.interaction.windows;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.game.Assets;
import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.manager.WindowManager;
import com.neweyjrpg.window.MessageWindow;

public class MessageInteraction extends WindowInteraction {
	
	private Array<String> message;
	public Array<String> getData() { return message; }
	private String font;
	
	public MessageInteraction(GameScene scene, String font, String str) {
		super(scene);
		this.font = font;
		this.message = this.formatString(str);
	}
	
	private Array<String> formatString(String str) {
		GlyphLayout layout 			= new GlyphLayout();
		BitmapFont f 				= Assets.loadFont(this.font);
		String[] split 				= str.split("\\s+");
		Array<String> ret 			= new Array<String>();
		StringBuilder builder 		= new StringBuilder("");
		StringBuilder lineBuilder 	= new StringBuilder("");
		
		for (int i=0, k=0; i<split.length; i++) {
			layout.setText(f, builder.toString() + " " + split[i]);
			if (layout.width + (Constants.WINDOW_PADDING * 2) > Constants.GAME_WIDTH) {
				lineBuilder.append(builder.toString() + "\n");
				builder.delete(0, builder.length());
				if (++k > 3) {
					ret.add(lineBuilder.toString());
					lineBuilder.delete(0, lineBuilder.length());
					k = 0;
				}
			}
			builder.append(" " + split[i]);
		}
		lineBuilder.append(builder.toString());
		ret.add(lineBuilder.toString());
		return ret;
	}
	
	public Interaction process(Manager m) {
		super.process(m);
		for (String s : this.getData()) {
			((WindowManager)m).addWindow(new MessageWindow(this, 0,0,Constants.GAME_WIDTH,80,this.font,s));
		}
		return this;
	}
}
