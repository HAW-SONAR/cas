package dataAccessLayer;

import dataAccessLayer.agents.IOma;
import dataAccessLayer.agents.IOpa;

import java.util.List;

public interface IOrganisation {

	public List<IOpa> getOpas();
	
	public List<IOma> getAllOmas();

	public void addOpa(IOpa opa);
	
	public void deleteOpa(IOpa opa);
	
	/**
	 * Returns all initial tasks
	 * @return
	 */
	public List<? extends IProtocol> getProtocols();
	
	/**
	 * For later than initial states of an organisation
	 * @param oma
	 */
	public void addOma(IOma oma);
	
	/**
	 * For later than initial states of an organisation
	 * @param oma
	 */
	public void deleteOma(IOma oma);

}
