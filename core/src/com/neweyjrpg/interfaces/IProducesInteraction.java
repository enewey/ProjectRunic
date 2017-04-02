package com.neweyjrpg.interfaces;

import java.util.List;

import com.neweyjrpg.interaction.Interaction;

public interface IProducesInteraction {
	public List<Interaction> onTouch();
	public List<Interaction> onAction();
}
