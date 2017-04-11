package com.neweyjrpg.interfaces;

import com.neweyjrpg.interaction.Interaction;

public interface IHandlesInteraction {
	
	public boolean handle(Interaction interaction);
	public void onInteractionComplete(Interaction i);

}
