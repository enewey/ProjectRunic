package com.neweyjrpg.interfaces;

import com.neweyjrpg.models.DirectionalInput;

/**
 * Interface that an actor can handle inputs from a class that implements IProducesInputs
 * @author erichnewey
 *
 */
public interface IHandlesInputs {
	
	public void moveFromInput(DirectionalInput dirs);

}
