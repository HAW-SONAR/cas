package dataAccessLayer.agents;

import java.util.List;

import dataAccessLayer.protocols.ConflictResolutionProtocol;
import dataAccessLayer.tasks.AssignedTask;
import dataAccessLayer.tasks.TeamOperation;
import dataAccessLayer.tasks.treeReconstruction.TFVForest;

//TODO document
public interface IOpa extends IAgent {
	public boolean isReady();
	
	public void addOperationConflictProtocol(ConflictResolutionProtocol<TeamOperation> protocol);

    public void addDelegationConflictProtocol(ConflictResolutionProtocol<String> protocol);
	
	public String getInducedTeamWorkflow();

    public TFVForest getTeamFormationView();
	
	public void setOpaProxies(List<IOpa> o);

	public void enqueue(AssignedTask task);

    public void enqueueRootTask();

	public boolean hasExecutableTasks();
}
