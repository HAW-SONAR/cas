package businessLogicLayer;

import businessLogicLayer.sonar.Sonar;
import businessLogicLayer.setUp.IOrganisationLoader;
import businessLogicLayer.setUp.XmlOrganisationLoader;
import dataAccessLayer.SonarOrganisation;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Main {

  public static void main(String [ ] args) {
    IOrganisationLoader orgLoader = new XmlOrganisationLoader();
    SonarOrganisation Org = orgLoader.loadOrganisation("res/organisationv2.xml");
    System.out.println(Org);
    //TODO instantiate sonar
    //Sonar sonar = new Sonar();
    //sonar.startSonar(Org);
  }
}
