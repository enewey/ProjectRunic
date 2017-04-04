package com.neweyjrpg.interaction.windows;

import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.manager.Manager;

public abstract class WindowInteraction extends Interaction {
	
	public WindowInteraction(GameScene scene) {
		super(scene);
	}

	public Interaction process(Manager m) {
		return super.process(m);
	}
}
