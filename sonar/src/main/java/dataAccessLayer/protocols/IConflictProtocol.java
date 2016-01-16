package dataAccessLayer.protocols;

import java.util.List;

public interface IConflictProtocol {
	
	public IConflictParameter<?> solveConflict(List<IConflictParameter<?>> params);
	
	public IConflictProtocolName getName();

}
