package dataAccessLayer.tasks;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public interface IRundes {

  public Operation getType();

  public List<InputTask> getInput();

  public List<OutputTask> getOutput();
}
