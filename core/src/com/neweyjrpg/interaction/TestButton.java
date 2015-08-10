package com.neweyjrpg.interaction;

import com.neweyjrpg.interaction.InteractionHandler;

public class TestButton implements InteractionHandler {

	private Interaction interaction;
	public TestButton(Interaction interaction) {
		this.interaction = interaction;
	}
	
	@Override
	public Interaction trigger() {
		return interaction;
	}

}
