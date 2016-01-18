package businessLogicLayer;

import businessLogicLayer.sonar.SonarExample;
import businessLogicLayer.setUp.IOrganisationLoader;
import businessLogicLayer.setUp.XmlOrganisationLoader;
import dataAccessLayer.SonarOrganisation;

//TODO document
/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Main {

  public static void main(String [ ] args) {
    IOrganisationLoader orgLoader = new XmlOrganisationLoader();
    SonarOrganisation Org = orgLoader.loadOrganisation("res/ExampleSlides.xml");
    String runName = "Demo_ExampleSlides";
    SonarExample sonar = new SonarExample(runName);
    sonar.startSonar(Org);
  }
}
