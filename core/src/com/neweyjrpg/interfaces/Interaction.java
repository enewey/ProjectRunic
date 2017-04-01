package com.neweyjrpg.interfaces;

import com.neweyjrpg.manager.Manager;

public interface Interaction {
	public Object getData();
	public Interaction process(Manager m);
}
