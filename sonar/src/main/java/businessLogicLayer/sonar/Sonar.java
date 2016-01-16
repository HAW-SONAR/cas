package businessLogicLayer.sonar;

import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.IOrganisation;
import dataAccessLayer.IProtocol;

public class Sonar implements ISonar {

	public Sonar() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Walks through the list of protocols and searchs for an opa that is ready
	 * to take that task for further computations
	 */
	@Override
	public void startSonar(IOrganisation organisation) {
		// TODO Auto-generated method stub
		
		List<? extends IOpa> opas = organisation.getAllOpas();
		System.out.println("Sonar start.");
		
		for(IOpa opa1 : opas){
			List<IOpa> opaReferences = new ArrayList<IOpa>();
			for(IOpa opa2 : opas){
				if(!opa2.getName().equals(opa1.getName())){
					opaReferences.add(opa2);
				}
			}
			opa1.setOpaProxys(opaReferences);
		}
		
		for(IOpa opa : opas){
			if(opa.getName().equals("O1")){
				opa.start();
				break;
			}
		}
		
		String workflow  = "";
		for(int i = 0; i < opas.size(); i++){
			if(!opas.get(i).getInducedTeamWorkflow().equals("")){
				if(!workflow.equals("")){
					workflow+= " + ";
				}
				workflow += opas.get(i).getInducedTeamWorkflow();
			}
			
		}
		System.out.println("");
		System.out.println("The induced Team-Workflow: ");
		System.out.println("D = " + workflow);

	}

}
