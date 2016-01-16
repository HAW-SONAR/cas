package dataAccessLayer.protocols;

import java.util.List;

public class IConflictProtocolImpl_DelegationConflict_Reliability implements
		IConflictProtocol {

	private IConflictProtocolName name;
	
	public IConflictProtocolImpl_DelegationConflict_Reliability(IConflictProtocolName name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public IConflictParameter<?> solveConflict(
			List<IConflictParameter<?>> params) {
		// TODO Auto-generated method stub
		IConflictParameter<?> agent_name = null;
		double r1_tmp = -1, r2_tmp = -1;
		
		for(int i = 0; i < params.size(); i++){
			r2_tmp = reliablity((String)params.get(i).getValue());
			
			if(r1_tmp == -1){
				r1_tmp = r2_tmp;
				agent_name = params.get(i);
			}else if(r2_tmp > r1_tmp){
				r1_tmp = r2_tmp;
			}
		}
		
		return agent_name;
	}

	private double reliablity(String agent_name) {
		// TODO ask meta-organisation for reliablity of agent agent_name 
		return 100;
	}

	@Override
	public IConflictProtocolName getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
