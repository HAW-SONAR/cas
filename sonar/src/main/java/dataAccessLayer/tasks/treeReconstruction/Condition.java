package dataAccessLayer.tasks.treeReconstruction;

import java.util.List;

public class Condition {

	List<List<String>> roles;
	
	public Condition(List<List<String>> roles) {
		// TODO Auto-generated constructor stub
		this.roles = roles;
	}
	
	public boolean fulfills(List<String> roles){
		for(List<String> r : this.roles){
			if(r.size() == roles.size()){
				if(r.containsAll(roles)){
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Condition [roles=" + roles + "]";
	}
	


}
