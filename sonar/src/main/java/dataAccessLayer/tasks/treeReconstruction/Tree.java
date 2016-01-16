package dataAccessLayer.tasks.treeReconstruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.InputRound;
import dataAccessLayer.tasks.OutputRound;

/**
 * Represents a tree of the applicable operations according to the
 * Team-Formation-View of the organization. This class provides access to
 * to all initial Tasks.
 * 
 * @author Francis Opoku, 16.01.2016
 */
public class Tree {

	HashMap<Condition, Transition> precond;
	HashMap<Condition, Transition> postcond;

	public Tree(List<IEckiges> tasks) {
		// TODO Auto-generated constructor stub
		precond = new HashMap<Condition, Transition>();
		postcond = new HashMap<Condition, Transition>();
		
		for (IEckiges e : tasks) {
			InputRound i = e.getInput().get(0);// there is just one input
			if (e.getOutput() != null) {
				this.insert(i, e, e.getOutput());
			} else {
				this.insert(i, e, null);
			}
		}
	}

	/**
	 * Inserts a new operation into a tree which represents an ordered structure
	 * of applicable operations related to a task
	 * 
	 * @param input
	 *            The input-parameter of the task
	 * @param operation
	 *            The operation itself
	 * @param output
	 *            The list of output-parameters
	 */
	public void insert(InputRound input, IEckiges operation,
			List<OutputRound> output) {
		Transition transition = new Transition(null, operation,
				new ArrayList<Place>());
		ArrayList<OutputRound> assigned = new ArrayList<OutputRound>();

		// check if there are some transitions with an input same as one
		// output of this operation. If so, set's the input-place as place of
		// the postset and the transition as the preset of this place.
		if (output != null)
			for (Condition precondition : precond.keySet()) {
				for (OutputRound out : output) {
					if (precondition.fulfills(out.getRole())) {
						precond.get(precondition).getPreset()
								.setPreset(transition);
						transition.addToPostset(precond.get(precondition)
								.getPreset());
						if (!assigned.contains(out)) {
							assigned.add(out);
						}
					}
				}
			}

		// maybe some outputs were not set as members of the postset of this
		// operation
		if (output != null)
			if (assigned.size() != output.size()) {
				for (OutputRound out : output) {
					if (!assigned.contains(out)) {
						Place p = new Place(null, null, out);
						transition.addToPostset(p);
						p.setPreset(transition);
					}
				}
			}

		// check if there are some transitions with an output as the input
		// of this operation
		boolean inputSet = false;
		for (Condition postcondition : postcond.keySet()) {
			if (postcondition.fulfills(input.getRoles())) {
				for (Place postPlace : postcond.get(postcondition).getPostset()) {
					if (postPlace.isInput()) {
						if (postPlace.getData1().getRoles().size() == input
								.getRoles().size()
								&& postPlace.getData1().getRoles()
										.containsAll(input.getRoles())) {
							postPlace.addToPostset(transition);
							transition.setPreset(postPlace);
							inputSet = true;
						}
					}
					if (postPlace.isOutput()) {
						if (postPlace.getData2().getRole().size() == input
								.getRoles().size()
								&& postPlace.getData2().getRole()
										.containsAll(input.getRoles())) {
							postPlace.addToPostset(transition);
							transition.setPreset(postPlace);
							inputSet = true;
						}
					}
				}
			}
		}

		// maybe there was no transition found which produces an output which
		// this operation needs as input
		if (!inputSet) {
			List<Transition> postset = new ArrayList<Transition>();
			postset.add(transition);
			Place p = new Place(null, postset, input);
			transition.setPreset(p);
		}

		// inserts this operation with its pre- and postconditions the the
		// referred
		// HashMap's.
		List<List<String>> inputroles = new ArrayList<List<String>>();
		inputroles.add(input.getRoles());
		precond.put(new Condition(inputroles), transition);
		List<List<String>> outputroles = new ArrayList<List<String>>();
		if (output != null)
			for (OutputRound out : output) {
				outputroles.add(out.getRole());
			}
		postcond.put(new Condition(outputroles), transition);
	}

	/**
	 * Returns the initial places
	 * 
	 * @return place The initial Place
	 */
	public List<Place> getInitialPlaces() {
		List<Place> initialPlaces = new ArrayList<Place>();

		for (Condition c : precond.keySet()) {
			if (precond.get(c).getPreset().getPreset() == null) {
				if (!initialPlaces.contains(precond.get(c).getPreset())) {
					initialPlaces.add(precond.get(c).getPreset());
				}
			}
		}

		return initialPlaces;
	}

	/**
	 * Returns all applicable operations for the task 'p'
	 * 
	 * @param p
	 *            The task
	 * @return operations The list of operations applicable to p
	 */
	public List<Transition> getApplicableOperations(Place p) {
		List<Transition> operations = new ArrayList<Transition>();
		for (Condition c : precond.keySet()) {
			if (precond.get(c).getPreset().equals(p)) {
				operations.add(precond.get(c));
			}
		}
		return operations;
	}

	/**
	 * Returns the postset of that operation applied on that task 'p'
	 * 
	 * @param p
	 *            The task
	 * @param operation
	 *            The operation
	 * @return places The list of Places in the postset of that operation
	 */
	public List<Place> applyOperation(Place p, Transition operation) {
		for (Condition c : postcond.keySet()) {
			if (postcond.get(c).equals(operation)
					&& postcond.get(c).getPreset().equals(p)) {
				if (postcond.get(c).getPostset() != null) {
					return postcond.get(c).getPostset();
				}
			}
		}
		return new ArrayList<Place>();
	}

	public HashMap<Condition, Transition> getPreconditions() {
		return precond;
	}

	public HashMap<Condition, Transition> getPostconditions() {
		return postcond;
	}

	@Override
	public String toString() {
		return "Tree [precond=" + precond
				+ System.getProperty("line.separator") + ", postcond="
				+ postcond + "]";
	}

}
