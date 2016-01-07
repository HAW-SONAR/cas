package dummy.interfaces;

public interface IOpa extends IAgent {
	
	/**
	 * Implementation of A*
	 * @return
	 */
	public IPlan computeGlobalPlan();
	
	public int getLevel();
	
	public IPlan exec(IProtocol p);
	
	public boolean deleg(IProtocol p);
	
	public boolean split(IProtocol p);
	
	public boolean refine(IProtocol p);
	
	public boolean isReady();
	
}
