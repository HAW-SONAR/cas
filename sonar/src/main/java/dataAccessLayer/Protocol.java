package dataAccessLayer;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.tasks.Operation;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 11.01.2016.
 */
public class Protocol implements IProtocol {

  private String name;
  private List<String> roles;
  private String file;

  public Protocol(String name, List<String> roles, String file) {
    this.name = name;
    this.roles = roles;
    this.file = file;
  }

  @Override public List<String> getRoles() {
    return this.roles;
  }

  @Override public List<Operation> getApplicableOperations() {
    return null;
  }

  @Override public boolean isAssigned() {
    return false;
  }

  @Override public IOpa getOwner() {
    return null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  @Override public String toString() {
    return "Protocol{" +
        "name='" + name + '\'' +
        ", roles=" + roles +
        ", file=" + file +
        '}';
  }

  @Override public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Protocol))
      return false;

    Protocol protocol = (Protocol) o;

    if (name != null ? !name.equals(protocol.name) : protocol.name != null)
      return false;
    if (roles != null ? !roles.equals(protocol.roles) : protocol.roles != null)
      return false;
    return !(file != null ? !file.equals(protocol.file) : protocol.file != null);

  }

  @Override public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (roles != null ? roles.hashCode() : 0);
    result = 31 * result + (file != null ? file.hashCode() : 0);
    return result;
  }
}
