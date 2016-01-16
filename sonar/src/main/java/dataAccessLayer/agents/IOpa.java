package dataAccessLayer.agents;

import java.util.List;

import dataAccessLayer.IPlan;
import dataAccessLayer.IProtocol;
import dataAccessLayer.protocols.IConflictProtocol;

public interface IOpa extends IAgent {
	
	/**
	 * Implementation of
	 * @return
	 */
	public IPlan computeGlobalPlan();
	
	public int getLevel();
	
	public IPlan exec(IProtocol p);
	
	public boolean deleg(IProtocol p);
	
	public boolean split(IProtocol p);
	
	public boolean refine(IProtocol p);
	
	public boolean isReady();
	
	public void setConflictProtocol(IConflictProtocol protocol);
	
	public String getInducedTeamWorkflow();
	
	public void setOpaProxys(List<IOpa> o);
	
}
