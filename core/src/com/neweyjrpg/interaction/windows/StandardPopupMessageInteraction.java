package com.neweyjrpg.interaction.windows;

import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.interfaces.IHandlesInteraction;

public class StandardPopupMessageInteraction extends PopupMessageInteraction {

	public StandardPopupMessageInteraction(IHandlesInteraction scene, GameActor source) {
		super(scene, 0,0,Constants.GAME_WIDTH, 80, source);
		// TODO Auto-generated constructor stub
	}

}
