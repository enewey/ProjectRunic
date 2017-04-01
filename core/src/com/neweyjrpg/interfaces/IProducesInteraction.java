package com.neweyjrpg.interfaces;

import java.util.List;

public interface IProducesInteraction {
	public List<Interaction> onTouch();
	public List<Interaction> onAction();
}
