package dataAccessLayer.tasks;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public interface ITask {

  public Operation getType();

  public List<TeamformationInput> getInput();

  public List<TeamformationOutput> getOutput();
}
