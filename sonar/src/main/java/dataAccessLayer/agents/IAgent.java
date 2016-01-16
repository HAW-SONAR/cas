package dataAccessLayer.agents;

import dataAccessLayer.IProtocol;

public interface IAgent {
	
	public String getName();
	
	/**
	 * An agent must extends Thread
	 */
	public void start();

}
