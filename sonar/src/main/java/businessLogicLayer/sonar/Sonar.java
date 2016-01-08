package dummy.implementations;

import java.util.List;

import dummy.interfaces.IOpa;
import dummy.interfaces.IOrganisation;
import dummy.interfaces.IProtocol;
import dummy.interfaces.ISonar;

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
		List<IOpa> opas = organisation.getAllOpas();
		List<IProtocol> protocols = organisation.getProtocols();
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
