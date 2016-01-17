package businessLogicLayer.sonar;

import dataAccessLayer.agents.IOpa;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public interface IOpaBlackBoard {

  /**
   * Registers an Opa to be found by others.
   * @param opa opa to be registered
   */
  public void registerOpa(IOpa opa);

  /**
   * Gets an Opa by his id.
   * @param id id of the opa we want.
   * @return Opa we look for.
   */
  public IOpa getOpaById(String id);
}
