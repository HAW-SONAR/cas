package dataAccessLayer.tasks;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public interface IEckiges {

  public Operation getType();

  public List<InputRound> getInput();

  public List<OutputRound> getOutput();
}
