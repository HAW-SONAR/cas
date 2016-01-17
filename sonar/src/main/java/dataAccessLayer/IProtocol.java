package dataAccessLayer;

import java.util.List;

/**
 * Should hold a petri net representation of the protocol
 * @author Francis
 *
 */
public interface IProtocol {

	public List<String> getRoles();

  public String getName();

  public String getFile();

}
