package dataAccessLayer.protocols;

import java.util.ArrayList;
import java.util.List;

import xmlRawClasses.Task;
import dataAccessLayer.tasks.Operation;

public class Test2 {

	public Test2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IConflictProtocolImpl_OperationConflict_MinMax p = new IConflictProtocolImpl_OperationConflict_MinMax(IConflictProtocolName.MINMAX);
		IConflictParameter<Operation> deleg = new IConflictParameterImpl<Operation>(Operation.DELEG);
		IConflictParameter<Operation> exec = new IConflictParameterImpl<Operation>(Operation.EXEC);
		Task t = new Task();
		IConflictParameter<Task> task = new IConflictParameterImpl<Task>(t);
		List<IConflictParameter<?>> params = new ArrayList<IConflictParameter<?>>();
		params.add(task);
		params.add(deleg);
		params.add(exec);
		
		p.solveConflict(params);
	}

}
