package businessLogicLayer;

import businessLogicLayer.SonarCycle.ISonar;
import businessLogicLayer.setUp.IOrganisationLoader;
import businessLogicLayer.setUp.XmlOrganisationLoader;
import dataAccessLayer.entities.Organisation;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Main {

  public static void main(String [ ] args) {
    IOrganisationLoader orgLoader = new XmlOrganisationLoader();
    Organisation Org = orgLoader.loadOrganisation("/path/");
    //TODO instantiate sonar
    ISonar sonar = null;
    sonar.startSonar(Org);
  }
}
