package com.neweyjrpg.interaction.windows;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.util.Funcs;

public class SpeechPopupInteraction extends PopupMessageInteraction {

	public SpeechPopupInteraction(IHandlesInteraction scene, int x, int y, String str, GameActor source) {
		super(scene, x, y, Constants.GAME_WIDTH/3, Funcs.getWindowHeight(str, Constants.DEFAULT_FONT), source);
	}

}
