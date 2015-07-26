package com.neweyjrpg.interfaces;

import com.neweyjrpg.actor.GameActor;

public interface IHandlesInteraction {
	
	public void onTouch(GameActor subject);
	public void onAction(GameActor subject);

}
