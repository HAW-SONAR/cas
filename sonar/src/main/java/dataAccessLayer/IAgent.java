package dummy.interfaces;

public interface IAgent {
	
	/**
	 * Get's the offer of this OPA for that protocol (required for A*)
	 * @param p
	 * @return o The cost from this OPA to handle the protocol p
	 */
	public double getOffer(IProtocol p);
	
	/**
	 * Sets the initial Task for the first OPA
	 * @param p
	 */
	public void setTask(IProtocol p);
	
	public String getName();
	
	/**
	 * An agent must extends Thread
	 */
	public void start();

}
