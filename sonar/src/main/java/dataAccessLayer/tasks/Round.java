package dataAccessLayer.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public class Round {

  protected String protocol;
  protected List<String> roles;
  protected String opaName;

  public Round(String protocol, List<String> role, String opaName) {
    this.protocol = protocol;
    this.roles = role;
    this.opaName = opaName;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String value) {
    this.protocol = value;
  }

  public List<String> getRoles() {
    if (roles == null) {
      roles = new ArrayList<String>();
    }
    return this.roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public String getOpaName() {
    return opaName;
  }

  public void setOpaName(String opaName) {
    this.opaName = opaName;
  }

  @Override public String toString() {
    return "Round{" +
        "protocol='" + protocol + '\'' +
        ", roles=" + roles +
        ", opaName='" + opaName + '\'' +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Round))
      return false;

    Round that = (Round) o;

    if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null)
      return false;
    if (roles != null ? !roles.equals(that.roles) : that.roles != null)
      return false;
    return !(opaName != null ? !opaName.equals(that.opaName) : that.opaName != null);

  }

  @Override public int hashCode() {
    int result = protocol != null ? protocol.hashCode() : 0;
    result = 31 * result + (roles != null ? roles.hashCode() : 0);
    result = 31 * result + (opaName != null ? opaName.hashCode() : 0);
    return result;
  }
}

