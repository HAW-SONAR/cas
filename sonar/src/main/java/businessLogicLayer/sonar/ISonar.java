package businessLogicLayer.sonar;

import dataAccessLayer.IOrganisation;
import dataAccessLayer.Organisation;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public interface ISonar {

  /**
   * Starts the Sonar process.
   * @param organisation Organisation that will be the subject.
   */
  public void startSonar(IOrganisation organisation);
}
