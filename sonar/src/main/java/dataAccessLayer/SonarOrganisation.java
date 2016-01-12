package dataAccessLayer;

import dataAccessLayer.agents.IOma;
import dataAccessLayer.agents.IOpa;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class SonarOrganisation implements IOrganisation {

  private List<IOpa> allOpas;
  private List<IProtocol> protocols;

  public SonarOrganisation() {
  }

  public SonarOrganisation(List<IOpa> allOpas, List<IProtocol> protocols) {
    this.allOpas = allOpas;
    this.protocols = protocols;
  }

  @Override public List<IOpa> getAllOpas() {
    return allOpas;
  }

  @Override public void addOpa(IOpa opa) {
    allOpas.add(opa);
  }

  @Override public void deleteOpa(IOpa opa) {
    if (allOpas.contains(opa)) {
      allOpas.remove(opa);
    } else {
      throw new IllegalStateException("Opa was not part of this SonarOrganisation");
    }
  }

  public void setAllOpas(List<IOpa> allOpas) {
    this.allOpas = allOpas;
  }

  public void setProtocols(List<IProtocol> protocols) {
    this.protocols = protocols;
  }

  @Override public List<IProtocol> getProtocols() {
    return protocols;
  }

  //IMPORANT: oma arent being implemented yet.

  @Override public void addOma(IOma oma) {
    // TODO Auto-generated method stub

  }

  @Override public void deleteOma(IOma oma) {
    // TODO Auto-generated method stub

  }

  @Override public List<IOma> getAllOmas() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override public String toString() {
    return "SonarOrganisation{" +
        "allOpas=" + allOpas +
        ", protocols=" + protocols +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof SonarOrganisation))
      return false;

    SonarOrganisation that = (SonarOrganisation) o;

    if (allOpas != null ? !allOpas.equals(that.allOpas) : that.allOpas != null)
      return false;
    return !(protocols != null ? !protocols.equals(that.protocols) : that.protocols != null);

  }

  @Override public int hashCode() {
    int result = allOpas != null ? allOpas.hashCode() : 0;
    result = 31 * result + (protocols != null ? protocols.hashCode() : 0);
    return result;
  }
}
