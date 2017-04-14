package com.neweyjrpg.interaction.windows;

import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.util.Funcs;

public class SpeechMessageInteraction extends MessageInteraction {

	int windowLines;
	
	public SpeechMessageInteraction(IHandlesInteraction scene, int x, int y, String font,
			String str) {
		super(scene, x, y, Constants.GAME_WIDTH/3, Funcs.getWindowHeight(str, font), font, str);
	}
}
