package dataAccessLayer;

import java.util.List;

/**
 * Should hold a petri net representation of the protocol
 * @author Francis
 *
 */
public interface IProtocol {
	
	public List<IRole> getRoles();
	
	public List<Operation> getApplicableOperations();
	
	/**
	 * True when the protocol has been assigned to an agent
	 * @return
	 */
	public boolean isAssigned();
	
	/**
	 * Each protocol (task) has an owner.
	 * @return o The owner of that task
	 */
	public IOpa getOwner();

}
