package dataAccessLayer.agents;

import dataAccessLayer.IPlan;
import dataAccessLayer.IProtocol;
import dataAccessLayer.tasks.ITask;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Opa implements IOpa {

  private Oma oma;
  private String name;
  private List<ITask> tasks;
  private List<String> resources;
  private List<String> communicationProtocols;

  public Opa(Oma oma, String name, List<ITask> tasks, List<String> resources,
      List<String> communicationProtocols) {
    this.oma = oma;
    this.name = name;
    this.tasks = tasks;
    this.resources = resources;
    this.communicationProtocols = communicationProtocols;
  }

  public Oma getOma() {
    return oma;
  }

  public void setOma(Oma oma) {
    this.oma = oma;
  }

  public void removeOma() {
    oma = null;
  }

  @Override public double getOffer(IProtocol p) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override public void setTask(IProtocol p) {
    // TODO Auto-generated method stub

  }

  @Override public String getName() {
    return name;
  }

  @Override public void start() {
    // TODO Auto-generated method stub

  }

  @Override public IPlan computeGlobalPlan() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override public int getLevel() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override public IPlan exec(IProtocol p) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override public boolean deleg(IProtocol p) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean split(IProtocol p) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean refine(IProtocol p) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean isReady() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ITask> getTasks() {
    return tasks;
  }

  public void setTasks(List<ITask> tasks) {
    this.tasks = tasks;
  }

  public List<String> getResources() {
    return resources;
  }

  public void setResources(List<String> resources) {
    this.resources = resources;
  }

  public List<String> getCommunicationProtocols() {
    return communicationProtocols;
  }

  public void setCommunicationProtocols(List<String> communicationProtocols) {
    this.communicationProtocols = communicationProtocols;
  }

  @Override public String toString() {
    return "Opa{" +
        "oma=" + oma +
        ", name='" + name + '\'' +
        ", tasks=" + tasks +
        ", resources=" + resources +
        ", communicationProtocols=" + communicationProtocols +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Opa))
      return false;

    Opa opa = (Opa) o;

    if (oma != null ? !oma.equals(opa.oma) : opa.oma != null)
      return false;
    if (name != null ? !name.equals(opa.name) : opa.name != null)
      return false;
    if (tasks != null ? !tasks.equals(opa.tasks) : opa.tasks != null)
      return false;
    if (resources != null ? !resources.equals(opa.resources) : opa.resources != null)
      return false;
    return !(communicationProtocols != null ?
        !communicationProtocols.equals(opa.communicationProtocols) :
        opa.communicationProtocols != null);

  }

  @Override public int hashCode() {
    int result = oma != null ? oma.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
    result = 31 * result + (resources != null ? resources.hashCode() : 0);
    result = 31 * result + (communicationProtocols != null ? communicationProtocols.hashCode() : 0);
    return result;
  }
}
