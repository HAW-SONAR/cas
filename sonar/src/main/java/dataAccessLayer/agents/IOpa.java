package dataAccessLayer.agents;

import java.util.List;

import dataAccessLayer.IPlan;
import dataAccessLayer.IProtocol;
import dataAccessLayer.protocols.IConflictProtocol;

public interface IOpa extends IAgent {

	public int getLevel();
	
	public boolean isReady();
	
	public void setConflictProtocol(IConflictProtocol protocol);
	
	public String getInducedTeamWorkflow();
	
	public void setOpaProxys(List<IOpa> o);
	
}
