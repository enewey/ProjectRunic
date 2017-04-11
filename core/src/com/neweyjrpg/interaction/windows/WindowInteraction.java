package com.neweyjrpg.interaction.windows;

import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.Manager;

public abstract class WindowInteraction extends Interaction {
	
	public WindowInteraction(IHandlesInteraction scene) {
		super(scene);
	}

	public Interaction process(Manager m) {
		return super.process(m);
	}
}
