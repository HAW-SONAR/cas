package businessLogicLayer.setUp;

import dataAccessLayer.SonarOrganisation;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public interface IOrganisationLoader {

  public SonarOrganisation loadOrganisation(String filepath);
}
