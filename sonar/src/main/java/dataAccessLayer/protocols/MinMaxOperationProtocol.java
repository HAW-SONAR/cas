package dataAccessLayer.protocols;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dataAccessLayer.tasks.InputRound;
import dataAccessLayer.tasks.TeamOperation;
import dataAccessLayer.tasks.OperationType;

public class MinMaxOperationProtocol implements ConflictResolutionProtocol<TeamOperation> {

	private float resources;
	private float minResources;
	private HashMap<OperationType, List<Long>> log; //OperationType -> how much time it took last time for that operation
	private ConflictResolutionProtocolName name;
	
	public MinMaxOperationProtocol(ConflictResolutionProtocolName name) {
		// TODO ask meta-organisation how much resources have to be free
		this.name = name;
		resources = 100;
		minResources = 0;
		log = new HashMap<OperationType, List<Long>>();
		Random r = new Random();
		//Just for presentation
		for(OperationType o : OperationType.values()){
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
	public ConflictOption<TeamOperation> solveConflict(
			List<ConflictOption<TeamOperation>> params) {
        //TODO
        //just pick any for testing
        return params.get(0);

		/*ConflictOption<TeamOperation> o_tmp = null;
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
		
		/*if((TeamOperation)o_tmp.getValue() == OperationType.EXEC){
			resources = resources();
			if(!(resources > minResources)){
				params.remove(o_tmp);
				o_tmp = solveConflict(params);
			}
		}*/ //TODO
		
		/*return o_tmp;*/
	}

	private long time(InputRound task,
			ConflictOption<TeamOperation> iConflictParameter) {
		long n = task.getRoles().size();
		long ri[] = getLog(iConflictParameter.getValue());
		long roles = ri[0];
		long time = ri[1];
		
		/*if((TeamOperation)iConflictParameter.getValue() == OperationType.DELEG){
			return 0; //TODO
			//return (time*n)/(Math.max(roles, 1)) + time(task, new PlainConflictOption<TeamOperation>(OperationType.EXEC));
		}*/
		
		return (time*n)/Math.max(roles, 1);
	}

	private long[] getLog(TeamOperation op) {
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
	public ConflictResolutionProtocolName getName(){
		return name;
	}







}