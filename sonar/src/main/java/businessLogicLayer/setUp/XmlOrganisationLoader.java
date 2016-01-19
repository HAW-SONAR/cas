package businessLogicLayer.setUp;

import dataAccessLayer.IProtocol;
import dataAccessLayer.Protocol;
import dataAccessLayer.SonarOrganisation;
import dataAccessLayer.agents.IOpa;
import dataAccessLayer.tasks.*;
import dataAccessLayer.tasks.Task;
import xmlRawClasses.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Enables loading of a SONAR organisation from an XML file.
 */
public class XmlOrganisationLoader implements IOrganisationLoader {
    @Override
    public SonarOrganisation loadOrganisation(String filepath) {
        Organisation result = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Organisation.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (Organisation) jaxbUnmarshaller.unmarshal(new File(filepath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return organisationConverter(result);
    }

    /**
     * Converts an organisation object structure (parser output) into an actual SONAR organisation.
     * @param xmlOrg Organisation object structure as parsed from XML
     * @return an equivalent SONAR organisation
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

        //Transfer all OPAs
        List<IOpa> opas = new ArrayList<>();
        for (Opa o : xmlOrg.getOpa()) {
            List<TeamOperation> operations = new ArrayList<>();
            //NOTE Transfer operations by pretending that they are tasks
            for (xmlRawClasses.Task taskOperation : o.getTask()) {
                TeamOperation operation = null;
                //NOTE why
                for (Exec e : taskOperation.getExec()) {
                    operation = new ExecOperation(new AssignedTask(new Task(e.getInput().getProtocol(),
                            e.getInput().getRole()),
                            o.getName()));
                    operations.add(operation);
                }
                for (Deleg d : taskOperation.getDeleg()) {
                    //NOTE can deleg have multiple inputs?
                    operation = new DelegOperation(new AssignedTask(new Task(d.getInput().get(0).getProtocol(),
                            d.getInput().get(0).getRole()),
                            o.getName()),
                            d.getTo());
                    operations.add(operation);
                }
                for (Refine r : taskOperation.getRefine()) {
                    operation = new RefineOperation(new AssignedTask(new Task(r.getInput().getProtocol(),
                            r.getInput().getRole()),
                            o.getName()),
                            new Task(r.getOutput().getProtocol(),
                                    r.getOutput().getRole()));
                    operations.add(operation);
                }
                for (Split s : taskOperation.getSplit()) {
                    List<Task> outputs = new ArrayList<>();
                    for (Output output : s.getOutput()) {
                        outputs.add(new Task(output.getProtocol(), output.getRole()));
                    }
                    operation = new SplitOperation(new AssignedTask(new Task(s.getInput().getProtocol(),
                            s.getInput().getRole()),
                            o.getName()),
                            outputs);
                    operations.add(operation);
                }
            }
            IOpa opa = new dataAccessLayer.agents.Opa(null,o.getName(),operations, o.getResource(),
                    o.getCommunicationProtocol());
            opas.add(opa);
        }
        result.setOpas(opas);
        return result;
    }
}
