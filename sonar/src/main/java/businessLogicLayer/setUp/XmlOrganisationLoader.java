package businessLogicLayer.setUp;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.IProtocol;
import dataAccessLayer.Protocol;
import dataAccessLayer.SonarOrganisation;
import dataAccessLayer.tasks.*;
import xmlRawClasses.*;
import xmlRawClasses.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
      System.out.println(result);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return organisationConverter(result);
  }

  /**
   * Converts an autogenerated xml Organisation into an actual Sonar Organisation.
   * @param xmlOrg XML parsed representation of an organisation
   * @return internal usable representation of an organisation.
   */
  public SonarOrganisation organisationConverter(Organisation xmlOrg) {
    SonarOrganisation result = new SonarOrganisation();
    // Transfer all protocols
    List<IProtocol> protocols = new ArrayList<>();
    for (xmlRawClasses.Protocol p : xmlOrg.getProtocol()) {
      Protocol protocol = new Protocol(p.getName(), p.getRole(), p.getFile());
      protocols.add(protocol);
    }
    result.setProtocols(protocols);

    //Transfer Opas
    List<IOpa> opas = new ArrayList<>();
    for (Opa o : xmlOrg.getOpa()) {
      List<IEckiges> tasks = new ArrayList<>();
      //Transfer Tasks
      for (Task t : o.getTask()) {
        IEckiges task;
        List<InputRound> inputs;
        List<OutputRound> outputs;
        // Convert Execs into tasks without Output
        for (Exec e : t.getExec()) {
          inputs = new ArrayList<>();
          inputs.add(new InputRound(e.getInput().getProtocol(),e.getInput().getRole()));
          task = new Eckiges(Operation.EXEC, inputs, null);
          tasks.add(task);
        }
        // Convert Delegs into tasks
        for (Deleg d : t.getDeleg()) {
          inputs = new ArrayList<>();
          for (Input i : d.getInput()) {
            inputs.add(new InputRound(i.getProtocol(),i.getRole()));
          }
          List<String> role = new ArrayList<>();
          role.add(d.getTo());
          outputs = new ArrayList<>();
          outputs.add(new OutputRound(null,role));
          task = new Eckiges(Operation.DELEG, inputs, outputs);
          tasks.add(task);
        }
        // Convert Refine into tasks
        for (Refine r : t.getRefine()) {
          inputs = new ArrayList<>();
          inputs.add(new InputRound(r.getInput().getProtocol(),r.getInput().getRole()));
          outputs = new ArrayList<>();
          outputs.add(new OutputRound(r.getOutput().getProtocol(),r.getOutput().getRole()));
          task = new Eckiges(Operation.REFINE, inputs, outputs);
          tasks.add(task);
        }
        // Convert Split into tasks
        for (Split s : t.getSplit()) {
          inputs = new ArrayList<>();
          inputs.add(new InputRound(s.getInput().getProtocol(),s.getInput().getRole()));
          outputs = new ArrayList<>();
          for (Output out : s.getOutput()) {
            outputs.add(new OutputRound(out.getProtocol(), out.getRole()));
          }
          task = new Eckiges(Operation.SPLIT, inputs, outputs);
          tasks.add(task);
        }
      }

      IOpa opa = new dataAccessLayer.agents.Opa(null,o.getName(),tasks, o.getResource(),
          o.getCommunicationProtocol());
      opas.add(opa);
    }
    result.setAllOpas(opas);
    return result;
  }
}
