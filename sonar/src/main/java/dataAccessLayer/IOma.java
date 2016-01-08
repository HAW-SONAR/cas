package dataAccessLayer;


public interface IOma extends IAgent{
	
	public IPlan getLocalPlan();
	
	public boolean hasLocalPlan();
	
	public IPlan exec(IProtocol p);

}
