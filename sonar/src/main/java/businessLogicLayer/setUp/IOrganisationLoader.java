package businessLogicLayer.setUp;

import dataAccessLayer.entities.Organisation;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public interface IOrganisationLoader {

  public Organisation loadOrganisation(String filepath);
}
