package businessLogicLayer.setUp;

import dataAccessLayer.SonarOrganisation;
import xmlRawClasses.Organisation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class XmlOrganisationLoader implements IOrganisationLoader {

  public SonarOrganisation loadOrganisation(String filepath) {
    Organisation result = null;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Organisation.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      result = (Organisation) jaxbUnmarshaller.unmarshal(new File(filepath));
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    System.out.println(result.getOpas().getOpa().get(0));
    return organisationConverter(result);
  }

  public SonarOrganisation organisationConverter(Organisation xmlOrg) {
    SonarOrganisation result = new SonarOrganisation();
    return result;
  }
}
