package com.neweyjrpg.interfaces;

/**
 * Interface that an actor can handle inputs from a class that implements IProducesInputs
 * @author erichnewey
 *
 */
public interface IHandlesInputs {
	
	public void moveFromInput(boolean[] dirs);

}
