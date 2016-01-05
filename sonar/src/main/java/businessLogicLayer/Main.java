package businessLogicLayer;

import businessLogicLayer.sonar.ISonar;
import businessLogicLayer.setUp.IOrganisationLoader;
import businessLogicLayer.setUp.XmlOrganisationLoader;
import dataAccessLayer.Organisation;

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
