package dataAccessLayer.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public class InputRound {

  protected String protocol;
  protected List<String> roles;

  public InputRound(String protocol, List<String> role) {
    this.protocol = protocol;
    this.roles = role;
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

  @Override public String toString() {
    return "InputRound{" +
        "protocol='" + protocol + '\'' +
        ", roles=" + roles +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof InputRound))
      return false;

    InputRound that = (InputRound) o;

    if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null)
      return false;
    return !(roles != null ? !roles.equals(that.roles) : that.roles != null);

  }

  @Override public int hashCode() {
    int result = protocol != null ? protocol.hashCode() : 0;
    result = 31 * result + (roles != null ? roles.hashCode() : 0);
    return result;
  }
}
