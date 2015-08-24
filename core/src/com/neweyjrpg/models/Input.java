package com.neweyjrpg.models;

public abstract class Input {
	
	protected float[] time;
	protected boolean[] data;
	
	public void clear() {
		if (data != null)
			for (int i=0; i<data.length; i++)
				this.data[i] = false;

		if (time != null)
			for (int i=0; i<time.length; i++)
				this.time[i] = 0f;
	}
	
	public boolean[] getInputs() {
		return data;
	}
	
	public float[] getInputTime() {
		return time;
	}
	
	public abstract boolean isEmpty();

}
