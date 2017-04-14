package com.neweyjrpg.interaction.windows;

import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.interfaces.IHandlesInteraction;

public class StandardMessageInteraction extends MessageInteraction {

	public StandardMessageInteraction(IHandlesInteraction scene, String font, String str) {
		super(scene, 0, 0, Constants.GAME_WIDTH, 80, font, str);
	}

}
