package com.neweyjrpg.interfaces;

import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public interface IProducesInputs {
	
	/**
	 * @return object that is guaranteed to represent what 
	 * 			directions are being pressed at the time of call
	 */
	public DirectionalInput getDirectionalState();
	
	/**
	 *  NOT DONE!
	 */
	public ButtonInput getButtonState();
}
