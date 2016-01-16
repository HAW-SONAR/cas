package dataAccessLayer.agents;

import dataAccessLayer.IProtocol;

public interface IAgent {
	
	/**
	 * Sets the initial Eckiges for the first OPA
	 * @param p
	 */
	public void setTask(IProtocol p);
	
	public String getName();
	
	/**
	 * An agent must extends Thread
	 */
	public void start();

}
