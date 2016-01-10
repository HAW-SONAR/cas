package businessLogicLayer.sonar;

import java.util.List;

import dataAccessLayer.IOpa;
import dataAccessLayer.IOrganisation;
import dataAccessLayer.IProtocol;

public class Sonar implements ISonar {

	public Sonar() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Walks through the list of protocols and searchs for an
	 * opa that is ready to take that task for further computations
	 */
	@Override
	public void startSonar(IOrganisation organisation) {
		// TODO Auto-generated method stub
		List<? extends IOpa> opas = organisation.getAllOpas();
		List<? extends IProtocol> protocols = organisation.getProtocols();
		int opaCounter = 0;
		int protocolCounter = 0;
		while (protocolCounter != protocols.size()) {
			for (IProtocol p : protocols) {
				if (!p.isAssigned()) {
					if (opaCounter < opas.size()) {
						if (opas.get(opaCounter).isReady()) {
							opas.get(opaCounter).setTask(p);
							opas.get(opaCounter).start();
						}
						opaCounter++;
					} else {
						opaCounter = 0;
					}
				}
			}
		}
	}

}
