package businessLogicLayer;

import businessLogicLayer.sonar.SonarExample;
import businessLogicLayer.setUp.IOrganisationLoader;
import businessLogicLayer.setUp.XmlOrganisationLoader;
import dataAccessLayer.SonarOrganisation;

/**
 * Entry point for SONAR demonstrations.
 */
public class Main {
    /**
     * Entry point for the program.
     * @param args args[0]: Relative path of the organisation file; args[1]: Name of the demonstration run
     */
    public static void main(String[] args) {
        IOrganisationLoader orgLoader = new XmlOrganisationLoader();
        SonarOrganisation Org = orgLoader.loadOrganisation(args[0]);
        SonarExample sonar = new SonarExample(args[1]);
        sonar.startSonar(Org);
    }
}
