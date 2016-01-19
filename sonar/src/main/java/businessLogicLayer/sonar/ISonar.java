package businessLogicLayer.sonar;

import dataAccessLayer.IOrganisation;

/**
 * SONAR instance that can execute the SONAR cycle for a SONAR organisation.
 */
public interface ISonar {

    /**
     * Starts the SONAR cycle for a SONAR organisation.
     * @param organisation The SONAR organisation
     */
    void startSonar(IOrganisation organisation);
}
