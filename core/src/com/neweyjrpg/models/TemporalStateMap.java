package com.neweyjrpg.models;

import java.util.HashMap;

public class TemporalStateMap extends HashMap<String, TemporalItem> {
	
	private static final long serialVersionUID = 1L;

	public TemporalItem get(String key) {
		if (!this.containsKey(key))
			this.put(key, new TemporalItem());
		return super.get(key);
	}

}
