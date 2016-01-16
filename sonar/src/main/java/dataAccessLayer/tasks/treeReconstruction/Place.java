package dataAccessLayer.tasks.treeReconstruction;

import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.tasks.InputRound;
import dataAccessLayer.tasks.OutputRound;

public class Place {

	private InputRound data1;
	private OutputRound data2;
	private Transition preset; //iff null -> initial place
	private List<Transition> postset;
	
	public Place(Transition preset, List<Transition> postset, InputRound data1) {
		// TODO Auto-generated constructor stub
		this.preset = preset;
		this.postset = postset;
		this.data1 = data1;
	}
	
	public Place(Transition preset, List<Transition> postset, OutputRound data2) {
		// TODO Auto-generated constructor stub
		this.preset = preset;
		this.postset = postset;
		this.data2 = data2;
	}
	
	public boolean isInput(){
		return data1 != null;
	}
	
	public boolean isOutput(){
		return data2 != null;
	}
	
	public Transition getPreset(){
		return preset;
	}
	
	public List<Transition> getPosteset(){
		return postset;
	}

	public void setPreset(Transition preset) {
		this.preset = preset;
	}

	public void setPostset(List<Transition> postset) {
		this.postset = postset;
	}
	
	public void addToPostset(Transition t){
		if(this.postset == null){
			postset = new ArrayList<Transition>();
		}
		postset.add(t);
	}

	public InputRound getData1() {
		return data1;
	}

	public OutputRound getData2() {
		return data2;
	}

	public List<Transition> getPostset() {
		return postset;
	}

	@Override
	public String toString() {
		return "Place [data1=" + data1 + ", data2=" + data2 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data1 == null) ? 0 : data1.hashCode());
		result = prime * result + ((data2 == null) ? 0 : data2.hashCode());
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
		Place other = (Place) obj;
		if (data1 == null) {
			if (other.data1 != null)
				return false;
		} else if (!data1.equals(other.data1))
			return false;
		if (data2 == null) {
			if (other.data2 != null)
				return false;
		} else if (!data2.equals(other.data2))
			return false;
		return true;
	}

}
