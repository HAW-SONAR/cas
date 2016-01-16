package dataAccessLayer.protocols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dataAccessLayer.tasks.InputRound;
import dataAccessLayer.tasks.Operation;

public class IConflictProtocolImpl_OperationConflict_MinMax implements IConflictProtocol {

	private float resources;
	private float minResources;
	private HashMap<Operation, List<Long>> log; //Operation -> how much time it took last time for that operation
	private IConflictProtocolName name;
	
	public IConflictProtocolImpl_OperationConflict_MinMax(IConflictProtocolName name) {
		// TODO ask meta-organisation how much resources have to be free
		this.name = name;
		resources = 100;
		minResources = 0;
		log = new HashMap<Operation, List<Long>>();
		Random r = new Random();
		//Just for presentation
		for(Operation o : Operation.values()){
			List<Long> values = new ArrayList<Long>();	
			values.add(r.nextLong());
			values.add(r.nextLong());
			log.put(o, values);
		}
	}
	
	/**
	 * 
	 * @param params contains the relevant parameters for solving the conflict. params.get(0) = Tasks, params.get(1..n) Operations.
	 * @return result a conflict parameter which contains the selected operation
	 */
	@Override
	public IConflictParameter<? extends Object> solveConflict(
			List<IConflictParameter<? extends Object>> params) {
		// TODO Auto-generated method stub
		IConflictParameter<? extends Object> o_tmp = null;
		long t1_tmp = -1, t2_tmp = -1;
		double resources;
		InputRound task = (InputRound) params.get(0).getValue();
		for(int i = 1; i < params.size(); i++){
			t2_tmp = time(task, params.get(i));
			
			if(t1_tmp == -1){
				t1_tmp = t2_tmp;
				o_tmp = params.get(i);
			}else if(t2_tmp < t1_tmp){
				t1_tmp = t2_tmp;
				o_tmp = params.get(i);
			}
		}
		
		if((Operation)o_tmp.getValue() == Operation.EXEC){
			resources = resources();
			if(!(resources > minResources)){
				params.remove(o_tmp);
				o_tmp = solveConflict(params);
			}
		}
		
		return o_tmp;
	}

	private long time(InputRound task,
			IConflictParameter<? extends Object> iConflictParameter) {
		// TODO Auto-generated method stub
		long n = task.getRoles().size();
		long ri[] = getLog((Operation) iConflictParameter.getValue());
		long roles = ri[0];
		long time = ri[1];
		
		if((Operation)iConflictParameter.getValue() == Operation.DELEG){
			return (time*n)/(Math.max(roles, 1)) + time(task, new IConflictParameterImpl<Object>(Operation.EXEC));
		}
		
		return (time*n)/Math.max(roles, 1);
	}

	private long[] getLog(Operation op) {
		// TODO Auto-generated method stub
		long[] ri = new long[2];
		if(!log.containsKey(op)){
			ri = new long[2];
			ri[0] = 0;
			ri[1] = 0;
		}else{
			ri[0] = log.get(op).get(0);
			ri[1] = log.get(op).get(1);
		}
		
		return ri;
	}

	private double resources() {
		// TODO Send message to Meta-Org and ask how much resources available
		return resources;
	}
	
	@Override
	public IConflictProtocolName getName(){
		return name;
	}





	

}
