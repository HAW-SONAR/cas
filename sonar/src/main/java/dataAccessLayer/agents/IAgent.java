package dataAccessLayer.agents;

import dataAccessLayer.IProtocol;
import dataAccessLayer.tasks.IEckiges;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public interface IAgent {

  public String getId();

  /**
   * Calculates the "cost" of a given "eckiges" operation.
   * @param eckiges operation that cost something.
   * @return cost
   */
  public int getEckigesOffer(IEckiges eckiges);
}
