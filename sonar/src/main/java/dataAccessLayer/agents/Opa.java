package dataAccessLayer.agents;

import dataAccessLayer.IPlan;
import dataAccessLayer.IProtocol;
import dataAccessLayer.protocols.IConflictParameter;
import dataAccessLayer.protocols.IConflictParameterImpl;
import dataAccessLayer.protocols.IConflictProtocol;
import dataAccessLayer.protocols.IConflictProtocolImpl_DelegationConflict_Reliability;
import dataAccessLayer.protocols.IConflictProtocolImpl_OperationConflict_MinMax;
import dataAccessLayer.protocols.IConflictProtocolName;
import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.InputRound;
import dataAccessLayer.tasks.Operation;
import dataAccessLayer.tasks.OutputRound;
import dataAccessLayer.tasks.treeReconstruction.Condition;
import dataAccessLayer.tasks.treeReconstruction.Place;
import dataAccessLayer.tasks.treeReconstruction.Transition;
import dataAccessLayer.tasks.treeReconstruction.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Opa implements IOpa {

	private Oma oma;
	private String name;
	private boolean isReady;
	// private List<IEckiges> tasks;
	private List<String> resources;
	private LinkedList<Place> taskStack;
	private List<String> communicationProtocols;
	private HashMap<IConflictProtocolName, IConflictProtocol> conflictProtocols;
	private IConflictProtocolName actualOperationConflictProtocol;
	private IConflictProtocolName actualDelegationConflictProtocol;
	private Tree tree;
	private HashMap<String, List<String>> execs;
	private List<IOpa> opaProxys;
	

	public Opa(Oma oma, String name, List<IEckiges> tasks,
			List<String> resources, List<String> communicationProtocols) {
		this.oma = oma;
		this.name = name;
		this.isReady = true;
		this.execs = new HashMap<String, List<String>>();
		tree = new Tree();
		System.out.println("Initialize " + this.getName());
		for (IEckiges e : tasks) {
			InputRound i = e.getInput().get(0);// there is just one input
			if (e.getOutput() != null) {
				tree.insert(i, e, e.getOutput());
			} else {
				tree.insert(i, e, null);
			}
		}
		// for(Transition t :
		// tree.getApplicableOperations(tree.getInitialPlace())){
		// System.out.println("APPLICABLE: " + t.getOperation().getType() +
		// " ON " + tree.getInitialPlace().getData1());
		// System.out.println("CHOSE " + t.getOperation().getType() +
		// " WITH OUTPUT: ");
		// for(Place p : tree.applyOperation(tree.getInitialPlace(), t)){
		// System.out.println(p);
		// }
		// }
		// System.out.println("");

		// System.out.println("=============Check-Tree======================");
		// check();
		// System.out.println("=============Check-Tree End==================");

		this.resources = resources;
		this.taskStack = new LinkedList<Place>();
		this.conflictProtocols = new HashMap<IConflictProtocolName, IConflictProtocol>();
		this.communicationProtocols = communicationProtocols;
		this.actualOperationConflictProtocol = IConflictProtocolName.MINMAX;
		this.actualDelegationConflictProtocol = IConflictProtocolName.RELIABILITY;
		this.conflictProtocols.put(actualOperationConflictProtocol,
				new IConflictProtocolImpl_OperationConflict_MinMax(
						actualOperationConflictProtocol));
		this.conflictProtocols.put(actualDelegationConflictProtocol,
				new IConflictProtocolImpl_DelegationConflict_Reliability(
						actualDelegationConflictProtocol));
	}

	private void check() {
		for (Condition c : tree.getPreconditions().keySet()) {
			if (tree.getPreconditions().get(c).getPreset().getPreset() == null) {
				System.out
						.println("PRESET NULL: "
								+ tree.getPreconditions().get(c).getPreset()
										.getData1()
								+ " "
								+ tree.getPreconditions().get(c).getPreset()
										.getData2());
			}
		}

		for (Condition c : tree.getPostconditions().keySet()) {
			if (tree.getPostconditions().get(c).getPostset() == null) {
				System.out.println("POSTSET NULL: "
						+ tree.getPostconditions().get(c).getPreset()
								.getData1()
						+ " "
						+ tree.getPostconditions().get(c).getPreset()
								.getData2());
			} else if (tree.getPostconditions().get(c).getPostset().size() == 0) {
				System.out.println("POSTSET EMPTY: "
						+ tree.getPostconditions().get(c).getPreset()
								.getData1()
						+ " "
						+ tree.getPostconditions().get(c).getPreset()
								.getData2());
			} else {

				System.out.println("POSTSET NOT NULL NOT EMPTY: "
						+ tree.getPostconditions().get(c).getPreset()
								.getData1()
						+ " "
						+ tree.getPostconditions().get(c).getPreset()
								.getData2()
						+ " "
						+ tree.getPostconditions().get(c).getOperation()
								.getType());
				for (Place p : tree.getPostconditions().get(c).getPostset()) {
					System.out.println("BECAUSE: " + p.getData1() + " "
							+ p.getData2());
				}
			}

		}
	}

	public Oma getOma() {
		return oma;
	}

	public void setOma(Oma oma) {
		this.oma = oma;
	}

	public void removeOma() {
		oma = null;
	}

	public void enqueue(Place p) {
		taskStack.push(p);
	}

	@Override
	public void setTask(IProtocol p) {
		// TODO
		// taskQueue.addLast(p);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		for (Place p : tree.getInitialPlaces()) {
			taskStack.push(p);
		}
		while (!taskStack.isEmpty()) {
			setIsReady(false);
			System.out.println(getName() + " start");
			teamFormation();
			setIsReady(true);
		}
	}

	private void teamFormation() {
		// TODO Auto-generated method stub
		while (!taskStack.isEmpty()) {
			Place place = taskStack.pop();
			if (place.getData1() == null) {
				System.out.println(this.getName() + " is working on Task "
						+ place.getData2().getRole());
			} else {
				System.out.println(this.getName() + " is working on Task "
						+ place.getData1().getRoles());
			}
			List<Transition> operations = tree.getApplicableOperations(place);

			String conflicts = "";
			for (Transition t : operations) {
				conflicts += t.getOperation().getType() + ", ";
			}
			System.out.println(this.getName() + " has applicable operations: "
					+ conflicts);

			Transition chosen = null;
			if (operations.size() > 1) {

				List<IConflictParameter<?>> params = new ArrayList<IConflictParameter<?>>();
				IConflictParameter<InputRound> input = new IConflictParameterImpl<InputRound>(
						place.getData1());
				params.add(input);
				List<Operation> ops = new ArrayList<Operation>();
				for (Transition t : operations) {
					if (!ops.contains(t.getOperation().getType())) {
						params.add(new IConflictParameterImpl<Operation>(t
								.getOperation().getType()));
						ops.add(t.getOperation().getType());
					}
				}

				@SuppressWarnings("unchecked")
				IConflictParameter<Operation> chosenWrapped = (IConflictParameter<Operation>) conflictProtocols
						.get(actualOperationConflictProtocol).solveConflict(
								params);

				System.out.println(this.getName() + " chose the operation "
						+ chosenWrapped.getValue());

				int delegables = 0;
				if (chosenWrapped.getValue() == Operation.DELEG) {
					for (Transition t : operations) {
						if (t.getOperation().getType() == Operation.DELEG) {
							delegables++;
						}
					}
					if (delegables > 1) {
						// TODO: Conflict protocol
						System.out.println(this.getName()
								+ " has a Delegation conflict ");
					}
				} else {
					for (Transition t : operations) {
						if (t.getOperation().getType() == chosenWrapped
								.getValue()) {
							chosen = t;
							break;
						}
					}
				}
			} else {
				chosen = operations.get(0);
				System.out.println(this.getName() + " chose the operation "
						+ chosen.getOperation().getType());
			}
			List<Place> places = tree.applyOperation(place, chosen);
			String result = "";
			for (Place p : places) {
				taskStack.push(p);
				if (p.getData1() == null) {
					result += p.getData2().getRole() + ", ";
				} else {
					result += p.getData1().getRoles() + ", ";
				}
			}
			
			if(chosen.getOperation().getType() == Operation.EXEC){
				if(place.getData1() == null){
					execs.put(place.getData2().getProtocol(), place.getData2().getRole());
				}else{
					execs.put(place.getData1().getProtocol(), place.getData1().getRoles());
				}
			}
			
			if(chosen.getOperation().getType() == Operation.DELEG){
				for(IOpa o : opaProxys){
					if(o.getName().equals(chosen.getOperation().getTo())){
						System.out.println("DELEG TO " + chosen.getOperation().getTo());
						o.start();
					}
				}
			}

			System.out.println(this.getName() + " the result is: " + result);

		}

	}

	@Override
	public IPlan computeGlobalPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPlan exec(IProtocol p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleg(IProtocol p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean split(IProtocol p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean refine(IProtocol p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return this.isReady;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IEckiges> getTasks() {
		return null;
	}

	public void setTasks(List<IEckiges> tasks) {
		// this.tasks = tasks;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public List<String> getCommunicationProtocols() {
		return communicationProtocols;
	}

	public void setCommunicationProtocols(List<String> communicationProtocols) {
		this.communicationProtocols = communicationProtocols;
	}

	private void setIsReady(boolean r) {
		this.isReady = r;
	}

	@Override
	public String toString() {
		return "Opa{" + "oma=" + oma + ", name='" + name + '\'' + ", tasks="
		/* + tasks */+ ", resources=" + resources + ", communicationProtocols="
				+ communicationProtocols + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Opa))
			return false;

		Opa opa = (Opa) o;

		if (oma != null ? !oma.equals(opa.oma) : opa.oma != null)
			return false;
		if (name != null ? !name.equals(opa.name) : opa.name != null)
			return false;
		/*
		 * if (tasks != null ? !tasks.equals(opa.tasks) : opa.tasks != null)
		 * return false;
		 */
		if (resources != null ? !resources.equals(opa.resources)
				: opa.resources != null)
			return false;
		return !(communicationProtocols != null ? !communicationProtocols
				.equals(opa.communicationProtocols)
				: opa.communicationProtocols != null);

	}

	@Override
	public int hashCode() {
		int result = oma != null ? oma.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		/* result = 31 * result + (tasks != null ? tasks.hashCode() : 0) */;
		result = 31 * result + (resources != null ? resources.hashCode() : 0);
		result = 31
				* result
				+ (communicationProtocols != null ? communicationProtocols
						.hashCode() : 0);
		return result;
	}

	@Override
	public void setConflictProtocol(IConflictProtocol protocol) {
		// TODO Auto-generated method stub
		conflictProtocols.put(protocol.getName(), protocol);
	}
	
	@Override
	public String getInducedTeamWorkflow(){
		String res = "";
		for(String protocol : execs.keySet()){
			res+= protocol + "" + execs.get(protocol);
		}
		return res;
	}

	@Override
	public void setOpaProxys(List<IOpa> o) {
		// TODO Auto-generated method stub
		this.opaProxys = o;
	}
}
