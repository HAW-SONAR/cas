package dataAccessLayer.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public class OutputRound {

  protected String protocol;
  protected List<String> role;
  private String opa;

  public OutputRound(String protocol, List<String> role) {
    this.protocol = protocol;
    this.role = role;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String value) {
    this.protocol = value;
  }

  public List<String> getRole() {
    if (role == null) {
      role = new ArrayList<String>();
    }
    return this.role;
  }

  @Override public String toString() {
    return "OutputRound{" +
        "protocol='" + protocol + '\'' +
        ", roles=" + role +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof OutputRound))
      return false;

    OutputRound that = (OutputRound) o;

    if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null)
      return false;
    return !(role != null ? !role.equals(that.role) : that.role != null);

  }

  @Override public int hashCode() {
    int result = protocol != null ? protocol.hashCode() : 0;
    result = 31 * result + (role != null ? role.hashCode() : 0);
    return result;
  }
}
