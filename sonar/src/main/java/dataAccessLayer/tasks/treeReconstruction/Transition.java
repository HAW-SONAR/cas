package dataAccessLayer.tasks.treeReconstruction;

import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.tasks.IEckiges;

public class Transition {

	private Place preset;
	private IEckiges operation;
	private List<Place> postset; //iff null/empty -> leaf (exec)
	
	public Transition(Place preset, IEckiges operation, List<Place> postset) {
		// TODO Auto-generated constructor stub
		this.preset = preset;
		this.operation = operation;
		this.postset = postset;
	}
	
	public Place getPreset(){
		return preset;
	}
	
	public List<Place> getPostset(){
		return postset;
	}

	public void setPreset(Place preset) {
		this.preset = preset;
	}

	public void setPostset(List<Place> postset) {
		this.postset = postset;
	}
	
	public void addToPostset(Place p){
		if(postset == null){
			this.postset = new ArrayList<Place>();
		}
		postset.add(p);
	}

	public IEckiges getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		return "Transition [preset=" + preset + System.getProperty("line.separator") + ", operation=" + operation
				+ ", postset=" + postset + "]" + System.getProperty("line.separator");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((preset == null) ? 0 : preset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transition other = (Transition) obj;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (preset == null) {
			if (other.preset != null)
				return false;
		} else if (!preset.equals(other.preset))
			return false;
		return true;
	}

}
