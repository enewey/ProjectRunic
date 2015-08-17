package com.neweyjrpg.models;

public abstract class Input {
	
	protected boolean[] data;
	
	public void clear() {
		if (data != null)
			for (int i=0; i<data.length; i++)
				this.data[i] = false;
	}
	
	public boolean[] getInputs() {
		return data;
	}
	
	public abstract boolean isEmpty();

}
