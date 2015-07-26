package com.neweyjrpg.interfaces;

public interface IProducesInputs {
	
	/**
	 * 0 - Up, 1 - Right, 2 - Down, 3 - Left
	 * @return four element array of booleans to indicate directions input
	 */
	public boolean[] getDirectionalInput();
	
	/**
	 * 0 - Button1, 1 - Button2, etc...
	 * @return ten element array of booleans
	 */
	public boolean[] getButtonInput();
	
}
